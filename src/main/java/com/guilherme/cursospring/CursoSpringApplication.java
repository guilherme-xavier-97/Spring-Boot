package com.guilherme.cursospring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.guilherme.cursospring.services.S3Service;


@SpringBootApplication
public class CursoSpringApplication implements CommandLineRunner { // Essa interface chama o método run automaticamente

	@Autowired
	private S3Service s3Service;

	public static void main(String[] args) {
		SpringApplication.run(CursoSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		s3Service.uploadFile("C:\\Users\\guilh\\OneDrive\\Documentos\\Spring Boot\\aws-logo.png");


	}
	
}
