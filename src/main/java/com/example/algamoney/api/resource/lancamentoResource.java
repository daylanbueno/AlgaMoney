package com.example.algamoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.event.RecursoCriadoEvent;
import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.service.LancamentoService;

@RestController
@RequestMapping("/lancamento")
public class lancamentoResource {

	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private ApplicationEventPublisher publicadorEvento;
	
	@PostMapping("/salvar")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Lancamento> salvar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response){
		Lancamento lancamentoSalvo = lancamentoService.salvar(lancamento);
		publicadorEvento.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}
	
	@GetMapping("/listar")
	public List<Lancamento> listar(){
		return lancamentoService.listar(); 
	}
	
	@GetMapping("/buscarPorCodigo/{codigo}")
	public ResponseEntity<Lancamento> buscaPorCodigo(@PathVariable Long codigo){
		Lancamento lancamentoRetornado = lancamentoService.buscarPorCodigo(codigo);
		return lancamentoRetornado != null ? ResponseEntity.ok(lancamentoRetornado): ResponseEntity.notFound().build();
	}
	
}
