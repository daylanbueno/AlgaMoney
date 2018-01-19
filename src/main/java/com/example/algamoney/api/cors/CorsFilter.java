package com.example.algamoney.api.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.algamoney.api.config.properties.ServiceProperties;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE) // Para ter preferencia nas requisições
public class CorsFilter implements Filter {
	
	@Autowired
	private ServiceProperties servProperties;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		resp.setHeader("Access-Control-Allow-Origin", servProperties.getOrigenPermitida()); 
		resp.setHeader("Access-Control-Allow-Credentials", "true"); 
		
		if("OPTIONS".equals(req.getMethod()) && servProperties.getOrigenPermitida().equals(req.getHeader("Origin"))) {
			
			resp.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS"); 
			resp.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept"); 
			resp.setHeader("Access-Control-Max-Age", "3600"); 

		}else {
			chain.doFilter(request,response);
		}
	}
	
	/*
	 * Implementação obrigatória devido a interface Filter.
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * Implementação obrigatória devido a interface Filter.
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
