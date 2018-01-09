package com.example.algamoney.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer; 

@Configuration //opecional já que @EnableWebSecurity já implementa essa anotation
@EnableWebSecurity
public class ResourceServeConfig  extends ResourceServerConfigurerAdapter {
	
	
	/**
	 * Responsavável  autenticar o usuário
	 */
	
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
		.withUser("admin").password("admin").roles("ROLE"); // ROLES É PERMISÕES.
		
	}
	
	
	/**
	 * Responsável por autorização das requisições
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
	
		http.authorizeRequests()
		.antMatchers("/categoria/listar").permitAll() // permitir acesso publico
		.anyRequest().authenticated() // Permitir acesso somente por usuário a todas as outras requisições
		.and()
	//	.httpBasic() // modo basico de requisição
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // não será criado sessão para o servidor.//
		.and()
		.csrf().disable();
	}
	
	/*
	 * Deixar servidor stateless
	 * (non-Javadoc)
	 * @see org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter#configure(org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer)
	 */
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.stateless(true);
	}
}
