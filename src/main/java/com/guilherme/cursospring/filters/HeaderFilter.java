package com.guilherme.cursospring.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class HeaderFilter implements Filter {

	//Se não expôr o Header, a aplicação do front-end pode ter problemas pra ler, então já deixa feito no back
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletResponse res = (HttpServletResponse) response;
		res.addHeader("access-control-expose-headers", "Location");
		chain.doFilter(request, response);
		
	}
	

}
