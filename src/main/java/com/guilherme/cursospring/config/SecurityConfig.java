package com.guilherme.cursospring.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.guilherme.cursospring.security.JWTAuthenticationFilter;
import com.guilherme.cursospring.security.JWTAuthorizationFilter;
import com.guilherme.cursospring.security.JWTUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private UserDetailsService userDetailService;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	//Esse vetor indica quais endpoint eu vou querer liberar acesso
	private static final String[] PUBLIC_MATCHERS = {
			"/h2-console/**",

	};
	
	//Esse vetor indica qual tipo de acesso vou liberar no endpoint, nesse caso só requisições do tipo GET são aceitas
	private static final String[] PUBLIC_MATCHERS_GET = {
			"/produtos/**",
			"/categorias/**",
	};
	
	//Esse vetor indica qual tipo de acesso vou liberar no endpoint, nesse caso só requisições do tipo POST são aceitas
		private static final String[] PUBLIC_MATCHERS_POST = {
				"/clientes/",
				"/auth/forgot/**"
		};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Se eu não fizer isso,não consigo acessar a URL do H2 no navegador
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers().frameOptions().disable();
        }
		
		//Eu desabilito a proteção contra CSRF que sao ataques feitos usando a seesão ativa, como o app é stateless, não guarda sessão, então não precisa dessa proteção
		http.cors().and().csrf().disable();
		//Esse método informa os endpoints liberados e diz que fora eles, todos os outros precisam de autenticação
		http.authorizeRequests()
			.antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
			.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
			.antMatchers(PUBLIC_MATCHERS).permitAll()
			.anyRequest().authenticated();
		
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailService));
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	//Como o app vai ser acessado por multiplas fontes (ambiente de teste, de desenvolvimento e produção) eu preciso liberar os acessos
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}


