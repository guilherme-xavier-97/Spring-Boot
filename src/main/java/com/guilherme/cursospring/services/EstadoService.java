package com.guilherme.cursospring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guilherme.cursospring.domain.Estado;
import com.guilherme.cursospring.repositories.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository repo;
	

	public List<Estado> readAll() {
		return repo.findAllByOrderByNome();
	}
	
	
	
	
	
}
