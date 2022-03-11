package com.guilherme.cursospring.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.guilherme.cursospring.domain.Categoria;
import com.guilherme.cursospring.domain.Produtos;

@Repository
public interface ProdutosRepository extends JpaRepository<Produtos, Integer> {

	@Transactional(readOnly = true)
	Page<Produtos> findDistinctByNomeContainingAndCategoriaIn(String nome, List<Categoria> categorias, Pageable pageRequest);
}
