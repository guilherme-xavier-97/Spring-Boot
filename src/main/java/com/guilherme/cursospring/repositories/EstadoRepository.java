package com.guilherme.cursospring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.guilherme.cursospring.domain.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

	//Cada atributo da entidade Estado pode gerar um método find prórpro, nem preciso criar
	@Transactional(readOnly=true)
	public List<Estado> findAllByOrderByNome();
}
