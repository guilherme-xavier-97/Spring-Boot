package com.guilherme.cursospring.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.guilherme.cursospring.domain.Pedido;
import com.guilherme.cursospring.repositories.PedidosRepository;
import com.guilherme.cursospring.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired //serve pra instanciar objetos por meio da injeção de dependecia
	private PedidosRepository repo;
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + 
		Pedido.class.getName()));
	}
}
