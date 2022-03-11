package com.guilherme.cursospring.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guilherme.cursospring.domain.Produtos;
import com.guilherme.cursospring.dto.ProdutoDTO;
import com.guilherme.cursospring.resources.utils.URL;
import com.guilherme.cursospring.services.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Produtos> read(@PathVariable Integer id) {

		Produtos obj = service.read(id);
		return ResponseEntity.ok().body(obj);

	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value = "nome", defaultValue = "0") String nome,
			@RequestParam(value = "categorias", defaultValue = "0") String categorias,
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, //24 é uma sugestão, pq é multiplo de 1, 2, 3 e 4, ai facilita na hora de montar um layout responsivo
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		String  decodeNome = URL.ajustarString(nome);
		List<Integer> ids = URL.converterStringEmLista(categorias);
		Page<Produtos> list = service.search(decodeNome, ids, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> listDTO = list.map(obj -> new ProdutoDTO(obj)); //o Page não precisa dos métodos stream e collect pra fazer as conversões
		
		return ResponseEntity.ok().body(listDTO); 
		
	}

}
