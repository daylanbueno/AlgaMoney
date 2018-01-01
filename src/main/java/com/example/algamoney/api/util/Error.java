package com.example.algamoney.api.util;

public class Error {
	
	private String msgDesenvolvedor;
	private String msgUsuario;

	public Error(String msgDesenvolvedor, String msgUsuario) {
		super();
		this.msgDesenvolvedor = msgDesenvolvedor;
		this.msgUsuario = msgUsuario;
	}
	public String getMsgDesenvolvedor() {
		return msgDesenvolvedor;
	}
	public String getMsgUsuario() {
		return msgUsuario;
	}
	
	
	
}
