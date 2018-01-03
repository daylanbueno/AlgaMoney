package com.example.algamoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.event.RecursoCriadoEvent;
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.service.PessoaService;

@RestController
@RequestMapping("/pessoa")
public class PessoaResource {


	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private ApplicationEventPublisher publicadorDeEvento;
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Pessoa> salvarPessoa(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		 Pessoa pessoaSalva = 	pessoaService.salvar(pessoa);
		 publicadorDeEvento.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
		 return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	 
	}

	@GetMapping("/find/{codigo}")
	public ResponseEntity<Pessoa> buscarPessoaPorId(@PathVariable Long codigo){
		Pessoa pRetornada = pessoaService.findPessoaByCodigo(codigo);
		return pRetornada !=null ? ResponseEntity.ok(pRetornada) : ResponseEntity.notFound().build(); 
	}
	

	@DeleteMapping("/remover/{codigo}") // entrega  nessa url. recebendo código como paramentro.
	@ResponseStatus(HttpStatus.NO_CONTENT) // Deu tudo certo..  mais eu não tenho conteudo para retorna.
	public void remover(@PathVariable Long codigo) {
		pessoaService.deletar(codigo);
	}
	
	@PutMapping("/atualizar/{codigo}")
	public ResponseEntity<Pessoa> atualizarPessoa(@PathVariable Long codigo, @RequestBody Pessoa pessoa){
		Pessoa pessoaAlterada = pessoaService.atualizar(codigo, pessoa);
		return ResponseEntity.ok(pessoaAlterada);
	}
	
	@PostMapping("/atualizaStatus/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)// deu tudo certo mais não tenho nada pra te mostrar.
	public void atualizaStatus(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		pessoaService.atualizaStatus(codigo, ativo);
	}
	
	@GetMapping("/listar")
	public List<Pessoa> listar(){
		return pessoaService.listaPessoa();
	}
	

}
