package com.guilherme.cursospring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guilherme.cursospring.domain.Cidades;
import com.guilherme.cursospring.repositories.CidadesRepository;

@Service
public class CidadesService {

	@Autowired
	private CidadesRepository repo;
	
	
	public List<Cidades> findByEstado(Integer estadoId) {
		return repo.findCidades(estadoId);
	}
}
