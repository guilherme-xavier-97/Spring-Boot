package com.guilherme.cursospring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.guilherme.cursospring.domain.Cliente;
import com.guilherme.cursospring.dto.ClienteDTO;
import com.guilherme.cursospring.repositories.ClienteRepository;
import com.guilherme.cursospring.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired //serve pra instanciar objetos por meio da injeção de dependecia
	private ClienteRepository repo;
	
	public Cliente readOne(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	public List<Cliente> readAll() {
		return repo.findAll();
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
					"Não foi possível " + "excluir porque esta entidade esta vinculada com outras");

		}

	}
	
	public Page<Cliente> findPage (Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	
	//Essa função converte um objeto tipo Cliente para tipo Cliente DTO
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);	
	}
	
	/*
	 Essa função atualiza no BD somente os campos que eu alterei, que no caso só estou liberando nome e email, os outros dados
	 como Tipo do cliente e se é PF ou PJ simplesmente são copiados do BD. obj são os dados do BD e newObj são os que serão alterados
	 */
	private void updateData(Cliente newObj, Cliente obj ) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		
	}
	
}
