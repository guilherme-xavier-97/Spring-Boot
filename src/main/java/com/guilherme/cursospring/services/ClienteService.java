package com.guilherme.cursospring.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.guilherme.cursospring.domain.Cidades;
import com.guilherme.cursospring.domain.Cliente;
import com.guilherme.cursospring.domain.Endereco;
import com.guilherme.cursospring.domain.enums.PerfilDeUsuario;
import com.guilherme.cursospring.domain.enums.TipoCliente;
import com.guilherme.cursospring.dto.ClienteDTO;
import com.guilherme.cursospring.dto.ClienteTelefoneEnderecoDTO;
import com.guilherme.cursospring.repositories.ClienteRepository;
import com.guilherme.cursospring.repositories.EnderecoRepository;
import com.guilherme.cursospring.security.UserSpringSecurity;
import com.guilherme.cursospring.services.exceptions.AuthorizationException;
import com.guilherme.cursospring.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired //serve pra instanciar objetos por meio da injeção de dependecia
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imageService;
	
	
	public Cliente readOne(Integer id) {
		//Confirmação se o cliente está logado, se ele tem perfil de ADMIN  e se ele está tentando ver os dados dele mesmo ou de outro cliente
		UserSpringSecurity user = UserService.authenticated();
		if (user==null || !user.hasRole(PerfilDeUsuario.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	public List<Cliente> readAll() {
		return repo.findAll();
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	
	public Cliente update(Cliente obj) {
		Cliente newObj = readOne(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		readOne(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new com.guilherme.cursospring.services.exceptions.DataIntegrityViolationException(
					"Não foi possível " + "excluir porque este cliente possui pedidos feitos");

		}

	}
	
	public Page<Cliente> findPage (Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	
	//Essa função converte um objeto tipo Cliente para tipo Cliente DTO
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null, null);	
	}
	
	public Cliente fromDTO(ClienteTelefoneEnderecoDTO objDTO) {
		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOucnpj(), 
				TipoCliente.toEnum(objDTO.getTipo()), bCryptPasswordEncoder.encode(objDTO.getSenha()));	
		
		Cidades cid = new Cidades(objDTO.getCidadeId(), null, null);
		
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), 
				objDTO.getBairro(), objDTO.getCep(), cli, cid);
		
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDTO.getTelefone1());
		if(objDTO.getTelefone2() != null) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		if(objDTO.getTelefone3() != null) {
			cli.getTelefones().add(objDTO.getTelefone3());
		}
		
		return cli;
	}
	
	/*
	 Essa função atualiza no BD somente os campos que eu alterei, que no caso só estou liberando nome e email, os outros dados
	 como Tipo do cliente e se é PF ou PJ simplesmente são copiados do BD. obj são os dados do BD e newObj são os que serão alterados
	 */
	private void updateData(Cliente newObj, Cliente obj ) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		
	}
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		//Confirmação se o cliente está logado
		UserSpringSecurity user = UserService.authenticated();
		if (user==null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		//Faz a imagem vinda na requisição ser do "tipo" BufferedImage e salva no S3
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		String fileName = multipartFile.getOriginalFilename();
		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");

		
	}
	
	//Esse método serve pra fazer uma busca por email, já que quando o usuario estiver fazendo as compras, os unicos dados que a sessão vai armazenar é o token e o email, pra servir de identificador do cliente, mais nada do BD
	public Cliente findByEmail(String email) {
		UserSpringSecurity user = UserService.authenticated();
		if (user == null || !user.hasRole(PerfilDeUsuario.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}

		Cliente obj = repo.findByEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Cliente.class.getName());
		}
		return obj;
	}
	
}
