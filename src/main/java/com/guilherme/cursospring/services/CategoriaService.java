package com.guilherme.cursospring.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guilherme.cursospring.domain.Categoria;
import com.guilherme.cursospring.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired //serve pra instanciar objetos por meio da injeção de dependecia
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElse(null);
	}
}
