package com.guilherme.cursospring.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.guilherme.cursospring.domain.Cliente;
import com.guilherme.cursospring.dto.ClienteDTO;
import com.guilherme.cursospring.dto.ClienteTelefoneEnderecoDTO;
import com.guilherme.cursospring.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClientesResource {
	
	@Autowired
	private ClienteService service;
		

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		
		Cliente obj = service.readOne(id);
		return ResponseEntity.ok().body(obj);
		
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> readAll() {
		
		List<Cliente> list = service.readAll();
		/*
		 O DTO serve pra manipular objetos. Nesse caso, na mihha classe "ClienteDTO" eu defini que os únicos objetos que quero
		 são o id e o nome, não quero os produtos atrelados às categorias. Desta forma, a lista geral de categorias (que vem com
		 os produtos juntos) é convertida em outra lista "listDTO", que vai receber uma arrow function que inscia a classe ClienteDTO
		 e retorna só os dados que ela permite (id e nome)
		 */
		List<ClienteDTO> listDTO = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO); 
		
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, //24 é uma sugestão, pq é multiplo de 1, 2, 3 e 4, ai facilita na hora de montar um layout responsivo
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		
		Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> listDTO = list.map(obj -> new ClienteDTO(obj)); //o Page não precisa dos métodos stream e collect pra fazer as conversões
		
		return ResponseEntity.ok().body(listDTO); 
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteTelefoneEnderecoDTO objDTO) { //O Request Body converte os dados JSON para um objeto JAVA
		Cliente obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		/*Esse método serve pra quando vc adicionar uma categoria nova, 
		automaticamente já gera a URI de acesso a ela. Fica na aba "Heade" do Insomnia */
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDTO, @PathVariable Integer id) { //O Request Body converte os dados JSON para um objeto JAVA
		Cliente obj = service.fromDTO(objDTO);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) { //O Request Body converte os dados JSON para um objeto JAVA
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
