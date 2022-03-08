package com.guilherme.cursospring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.guilherme.cursospring.domain.Pedido;

@Repository
public interface PedidosRpository extends JpaRepository<Pedido, Integer>{

}
