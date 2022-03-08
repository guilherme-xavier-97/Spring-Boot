package com.guilherme.cursospring;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.guilherme.cursospring.domain.Categoria;
import com.guilherme.cursospring.domain.Cidades;
import com.guilherme.cursospring.domain.Estado;
import com.guilherme.cursospring.domain.Produtos;
import com.guilherme.cursospring.repositories.CategoriaRepository;
import com.guilherme.cursospring.repositories.CidadesRepository;
import com.guilherme.cursospring.repositories.EstadoRepository;
import com.guilherme.cursospring.repositories.ProdutosRepository;

@SpringBootApplication
public class CursoSpringApplication implements CommandLineRunner { // Essa interface chama o método run automaticamente
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutosRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadesRepository cidadesRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursoSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produtos p1 = new Produtos(null, "Computador", 2000.00);
		Produtos p2 = new Produtos(null, "Impressora", 800.00);
		Produtos p3 = new Produtos(null, "Mouse", 80.00);
		
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategoria().addAll(Arrays.asList(cat1));
		p2.getCategoria().addAll(Arrays.asList(cat1, cat2));
		p3.getCategoria().addAll(Arrays.asList(cat1));
		
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2)); // esse Arrays as list permite que eu salve varios objetos de uma vez
		
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidades c1 = new Cidades(null, "Uberlândia", est1);
		Cidades c2 = new Cidades(null, "São Paulo", est2);
		Cidades c3 = new Cidades(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		
		cidadesRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		
	}

}
