package com.example.algamoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.event.RecursoCriadoEvent;
import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.service.CategoriaService;


@RestController
@RequestMapping("/categoria")
public class CategoriaResource {
	
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private ApplicationEventPublisher publicadorDeEvento;
	
	@GetMapping("/listar")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')") // Para acessar esse método o usuario deverá ter a permisão  ROLE_PESQUISAR_CATEGORIA
	public List<Categoria> listar(){
		return categoriaService.listar();
	}
	
	/**
	 * 
	 * @param categoria
	 * @param response
	 * @return
	 * @valid para validar se é nulo
	 * @RequestBondy para receber paramentro
	 */
	@PostMapping("/salvar")	
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA')")
	@ResponseStatus(org.springframework.http.HttpStatus.CREATED)
	public ResponseEntity<Categoria> salvar(@Valid @RequestBody Categoria categoria, HttpServletResponse  response) {
	    Categoria categoriaSalva = categoriaService.salvar(categoria);
	    publicadorDeEvento.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva); 
	}
	
	@GetMapping("/find/{codigo}")
	public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable  Long codigo) {
		Categoria catRestornada= categoriaService.findByCodigo(codigo);
		return catRestornada != null ? ResponseEntity.ok(catRestornada) : ResponseEntity.notFound().build();
	}
	
}
