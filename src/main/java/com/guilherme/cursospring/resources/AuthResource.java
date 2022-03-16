package com.guilherme.cursospring.resources;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.guilherme.cursospring.security.JWTUtil;
import com.guilherme.cursospring.security.UserSpringSecurity;
import com.guilherme.cursospring.services.UserService;


/*Esse Resource serve para rneovar o token sempre que ele estiver para expirar, assim enquanto vc estiver logado, o 
token é atualizado e vc não precisa ficar fazendo login toda hora. 
É tipo o acesso do facebook,mesmo q vc feche a aba, contnua logado */

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {
	
	@Autowired
	private JWTUtil jwtUtil;

	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSpringSecurity user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", token);
		return ResponseEntity.noContent().build();
	}
}
