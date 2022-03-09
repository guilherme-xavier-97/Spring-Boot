package com.guilherme.cursospring.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.guilherme.cursospring.domain.Cliente;
import com.guilherme.cursospring.repositories.ClienteRepository;
import com.guilherme.cursospring.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired //serve pra instanciar objetos por meio da injeção de dependecia
	private ClienteRepository repo;
	
	public Cliente read(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + 
		Cliente.class.getName()));
	}
}
