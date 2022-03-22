package com.guilherme.cursospring.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.guilherme.cursospring.domain.Cidades;
import com.guilherme.cursospring.domain.Estado;
import com.guilherme.cursospring.dto.CidadesDTO;
import com.guilherme.cursospring.dto.EstadoDTO;
import com.guilherme.cursospring.services.CidadesService;
import com.guilherme.cursospring.services.EstadoService;


@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {

	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private CidadesService cidadesService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> readAll() {
		
		List<Estado> list = estadoService.readAll();
		/*
		 O DTO serve pra manipular objetos. Nesse caso, na mihha classe "ClienteDTO" eu defini que os únicos objetos que quero
		 são o id e o nome, não quero os produtos atrelados às categorias. Desta forma, a lista geral de categorias (que vem com
		 os produtos juntos) é convertida em outra lista "listDTO", que vai receber uma arrow function que inscia a classe ClienteDTO
		 e retorna só os dados que ela permite (id e nome)
		 */
		List<EstadoDTO> listDTO = list.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO); 
		
	}
	
	
	@RequestMapping(value = "/{estadoId}/cidades", method = RequestMethod.GET)
	public ResponseEntity<List<CidadesDTO>> findCidade(@PathVariable Integer estadoId) {
		
		List<Cidades> list = cidadesService.findByEstado(estadoId);
		List<CidadesDTO> listDto = list.stream().map(obj -> new CidadesDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
		
	}
}
