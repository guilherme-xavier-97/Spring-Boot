package com.guilherme.cursospring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.guilherme.cursospring.domain.Categoria;
import com.guilherme.cursospring.domain.Produtos;
import com.guilherme.cursospring.repositories.CategoriaRepository;
import com.guilherme.cursospring.repositories.ProdutosRepository;
import com.guilherme.cursospring.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired //serve pra instanciar objetos por meio da injeção de dependecia
	private ProdutosRepository repo;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Produtos read(Integer id) {
		Optional<Produtos> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + 
		Produtos.class.getName()));
	}
	
	public Page<Produtos> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, 
			String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return repo.findDistinctByNomeContainingAndCategoriaIn(nome, categorias, pageRequest);
		
	}
}
