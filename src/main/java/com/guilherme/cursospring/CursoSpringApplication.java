package com.guilherme.cursospring;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.guilherme.cursospring.domain.Categoria;
import com.guilherme.cursospring.repositories.CategoriaRepository;

@SpringBootApplication
public class CursoSpringApplication implements CommandLineRunner { // Essa interface chama o método run automaticamente
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursoSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2)); // esse Arrays as list permite que eu salve varios objetos de uma vez
		
	}

}
