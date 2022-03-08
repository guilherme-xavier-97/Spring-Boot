package com.guilherme.cursospring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.guilherme.cursospring.domain.Produtos;

@Repository
public interface ProdutosRepository extends JpaRepository<Produtos, Integer> {

}
