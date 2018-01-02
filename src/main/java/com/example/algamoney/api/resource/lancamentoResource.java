package com.example.algamoney.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.service.LancamentoService;

@RestController
@RequestMapping("/lancamento")
public class lancamentoResource {

	@Autowired
	private LancamentoService lancamentoService;
	
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
