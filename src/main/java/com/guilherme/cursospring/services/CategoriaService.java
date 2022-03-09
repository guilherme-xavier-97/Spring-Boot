package com.guilherme.cursospring.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guilherme.cursospring.domain.Categoria;
import com.guilherme.cursospring.repositories.CategoriaRepository;
import com.guilherme.cursospring.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired //serve pra instanciar objetos por meio da injeção de dependecia
	private CategoriaRepository repo;
	
	public Categoria read(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + 
		Categoria.class.getName()));
	}
	
	public Categoria insert (Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
}
