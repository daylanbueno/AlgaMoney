package com.example.algamoney.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration //opecional já que @EnableWebSecurity já implementa essa anotation
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter{
	
	
	/**
	 * Responsavável  autenticar o usuário
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
		.withUser("admin").password("admin").roles("ROLE"); // ROLES É PERMISÕES.
		
	}
	
	
	/**
	 * Responsável por autorização das requisições
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		http.authorizeRequests()
		.antMatchers("/categoria/listar").permitAll() // permitir acesso publico
		.anyRequest().authenticated() // Permitir acesso somente por usuário a todas as outras requisições
		.and()
		.httpBasic() // modo basico de requisição
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // não será criado sessão para o servidor.
		.and()
		.csrf().disable();
	}
}
