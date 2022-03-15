package com.guilherme.cursospring.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expiration;
	
	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username) //informa quem esta gerando o token
				.setExpiration(new Date(System.currentTimeMillis() + expiration)) //a partir do momento atual, soma 1 minuto para expirar
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()) //Escolhe o algoritmo usado para gerar o token
				.compact();
	}
}
