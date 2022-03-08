package com.guilherme.cursospring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.guilherme.cursospring.domain.Cidades;

@Repository
public interface CidadesRepository extends JpaRepository<Cidades, Integer> {
	
}