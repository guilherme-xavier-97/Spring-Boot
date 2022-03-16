package com.guilherme.cursospring.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.guilherme.cursospring.security.UserSpringSecurity;

public class UserService {
	
	public static UserSpringSecurity authenticated() {
		//Esse método confirma quem é o usuario logado
		try {
			return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch (Exception e) {
			return null;
		}
		
	}

}
