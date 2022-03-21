package com.guilherme.cursospring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.guilherme.cursospring.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	//Cada atributo da entidade Cliente pode gerar um método find prórpro, nem preciso criar
	@Transactional(readOnly = true)
	Cliente findByEmail(String email);
	

}
