package com.example.algamoney.api.resource;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.config.properties.ServiceProperties;
/**
 * 
 * @author Daylan Bueno
 *	 Logout  é simplismente remover token do brawser.
 */
@RestController
@RequestMapping("/token")
public class TokenResource {
	
	@Autowired
	private ServiceProperties servProperties;
	

	@DeleteMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie  = new Cookie("refreshToken", null);
		cookie.setHttpOnly(true);
		cookie.setSecure(servProperties.getSeguranca().isEnableHttps());// TODO: em produção deverá ser true
		cookie.setPath(request.getContextPath()+ "/oauth/token");
		cookie.setMaxAge(0); // em quanto tempo vai expirar?
		response.addCookie(cookie); // adicionando cookie vazio na responsta.
		response.setStatus(HttpStatus.NO_CONTENT.value()); //  aplicando status para responta.
	}
}
