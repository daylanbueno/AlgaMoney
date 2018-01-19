package com.example.algamoney.api.token;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.example.algamoney.api.config.properties.ServiceProperties;

@ControllerAdvice
public class RefreshTokenPostProcessor  implements ResponseBodyAdvice<OAuth2AccessToken>{// Sempre que um corpo de responta for ResponseBodyAdvice<OAuth2AccessToken>  vou intecptar essa responsta

	@Autowired
	private ServiceProperties servProperties;
	
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return returnType.getMethod().getName().equals("postAccessToken");// adicionando filtro  para exceutar OAuth2AccessToken
	}

	@Override
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter returnType,
			MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {
		
		HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest(); 
		HttpServletResponse resp = ((ServletServerHttpResponse) response).getServletResponse();
		
		DefaultOAuth2AccessToken token  = (DefaultOAuth2AccessToken) body; // cat nescessário para poder  setar null no corpo..
		
		String refreshToken = body.getRefreshToken().getValue(); // pegando valor do refresh token.
		
		adicionarRefreshTokenNoCookie(refreshToken , resp,req);
		removerRefreshTokneNoBody(token);
		
		
		return body;	
	}

	private void adicionarRefreshTokenNoCookie(String refreshToken, HttpServletResponse resp, HttpServletRequest req) {
		Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
		refreshTokenCookie.setHttpOnly(true); // 	 so pode ser acessado em http.
		refreshTokenCookie.setSecure(servProperties.getSeguranca().isEnableHttps());// TODO:dever ser seguro  pode ser acessado somente em https?
		refreshTokenCookie.setPath(req.getContextPath()+ "/oauth/token"); // para qual caminho ele vai mandar
														
		refreshTokenCookie.setMaxAge(259200);// em quanto tempo esse cookie vai expirar
		resp.addCookie(refreshTokenCookie);  // adiconando cookie na resposta.
	}
	private void removerRefreshTokneNoBody(DefaultOAuth2AccessToken token) {
		token.setRefreshToken(null); // onde remove refresh token do corpo.
	}
	

	
}
