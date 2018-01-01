package com.example.algamoney.api.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.example.algamoney.api.util.Error;


/**
 * 
 * @author Daylan Bueno
 * Classe responável por tratar exception
 */
// observa toda a aplicação
@ControllerAdvice // essa anotação  para funcionar e cai nessa classe quando da exception
public class AlgamoneyExceptionHandler extends ResponseEntityExceptionHandler {
	
	// comando para injeção
	@Autowired
	private MessageSource messageSorce;
	
	/*
	 *Quando não conseguie  converter o objeto  json para Entidade 
	 *Quando  manda um atributo que não existe na entidade 
	 *da erro de conversão de objeto
	 *e tratado por essa classe
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,HttpHeaders headers, HttpStatus status, WebRequest request) {
		String msgUsuario=  messageSorce.getMessage("entidade.invalida",null,LocaleContextHolder.getLocale());
		String msgDesenvolvedor =  ex.getCause().getMessage();
		List<Error> erros = Arrays.asList(new Error(msgDesenvolvedor, msgUsuario));
		return handleExceptionInternal(ex,erros,headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<Error> erros =  new ArrayList<>();
		 erros = listarErros(ex.getBindingResult());
		return  handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({EmptyResultDataAccessException.class}) // pegando esse tipo de exception
	//@ResponseStatus(HttpStatus.NOT_FOUND) // 	NOT_FOUND: vc ta tendando acessa um recuso que não existe, erro do lado do cliente.
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,WebRequest request) {
		String msgUsuario=  messageSorce.getMessage("recurso.nao.encontrado",null,LocaleContextHolder.getLocale());
		String msgDesenvolvedor =  ex.toString();
		List<Error> erros = Arrays.asList(new Error(msgDesenvolvedor, msgUsuario));
		return handleExceptionInternal(ex,erros,new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	private List<Error> listarErros(BindingResult bindingResult){
		List<Error> erros =  new ArrayList<>();
		
		for (FieldError field : bindingResult.getFieldErrors()) {
			String msgUsuario = messageSorce.getMessage(field,LocaleContextHolder.getLocale());
			String msgDesenvolvedor = field.toString();
			erros.add(new Error(msgDesenvolvedor, msgUsuario));
		}
		
		return erros;
	}
}
