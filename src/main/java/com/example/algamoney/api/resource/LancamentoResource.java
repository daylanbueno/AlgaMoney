package com.example.algamoney.api.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.event.RecursoCriadoEvent;
import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.repository.filter.LancamentoFilter;
import com.example.algamoney.api.service.LancamentoService;
import com.example.algamoney.api.service.exception.PessoaInativaOuNaoExiste;
import com.example.algamoney.api.util.Error;

@RestController
@RequestMapping("/lancamento")
public class LancamentoResource {

	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private ApplicationEventPublisher publicadorEvento;
	
	@Autowired
	private MessageSource  messageSorce;
	
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
	
	@ExceptionHandler({PessoaInativaOuNaoExiste.class})
	public ResponseEntity<Object> handlerPessoaInativaOuNaoExiste(PessoaInativaOuNaoExiste ex){
		String mensagemUsuario = messageSorce.getMessage("operacao-nao-permitida", null, LocaleContextHolder.getLocale()); 
		String mensagemDesenvolvedor = ex.toString();
		List<Error> erros = Arrays.asList(new Error(mensagemUsuario, mensagemDesenvolvedor)); 
		return ResponseEntity.badRequest().body(erros);
	}
	
	@GetMapping("/pesquisar")
	public  List<Lancamento> pesquisarPorFitro(LancamentoFilter lancamentoFilter ){
		return lancamentoService.pesquisar(lancamentoFilter);
	}
	
	@DeleteMapping("/apagar/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)// deu tudo certo mais eu não tenho nada para te mostrar.
	public void deletar(@PathVariable Long codigo) {
		lancamentoService.remover(codigo);
	}
}
