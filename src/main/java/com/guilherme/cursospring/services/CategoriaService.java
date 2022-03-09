package com.guilherme.cursospring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.guilherme.cursospring.domain.Categoria;
import com.guilherme.cursospring.dto.CategoriaDTO;
import com.guilherme.cursospring.repositories.CategoriaRepository;
import com.guilherme.cursospring.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired // serve pra instanciar objetos por meio da injeção de dependecia
	private CategoriaRepository repo;

	public Categoria readOne(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	public List<Categoria> readAll() {
		return repo.findAll();
	}

	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public Categoria update(Categoria obj) {
		Categoria newObj = readOne(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		readOne(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new com.guilherme.cursospring.services.exceptions.DataIntegrityViolationException(
					"Não foi possível " + "excluir esta categoria pois ela está vinculada a algum produto");

		}

	}
	
	public Page<Categoria> findPage (Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	
	//Essa função converte um objeto tipo Categoria para tipo Categoria DTO
	public Categoria fromDTO(CategoriaDTO objDTO) {
		return new Categoria(objDTO.getId(), objDTO.getNome());	
	}
	
	/*
	 Essa função atualiza no BD somente os campos que eu alterei, que no caso só estou liberando o nome, os outros dados
	 como Estado de pagamento simplesmente são copiados do BD. obj são os dados do BD e newObj são os que serão alterados
	 */
	private void updateData(Categoria newObj, Categoria obj ) {
		newObj.setNome(obj.getNome());		
	}
}
