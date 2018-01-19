package com.example.algamoney.api.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("money") // dependencia obrigatória.
public class ServiceProperties {

	private Seguranca seguranca = new Seguranca();
	
	private String origenPermitida = "http://localhost:8000";
	
	public Seguranca getSeguranca() {
		return seguranca;
	}
	
	
	public String getOrigenPermitida() {
		return origenPermitida;
	}

	public void setOrigenPermitida(String origenPermitida) {
		this.origenPermitida = origenPermitida;
	}




	public  static class Seguranca{
		
		private boolean enableHttps;

		public boolean isEnableHttps() {
			return enableHttps;
		}

		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}
		 
	}


}
