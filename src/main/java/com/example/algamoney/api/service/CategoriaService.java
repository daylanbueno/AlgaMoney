package com.example.algamoney.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.repository.CategoriaRepository;

@Service
public class CategoriaService {

	
	@Autowired
	private CategoriaRepository categoriaRepository;

	public Categoria salvar(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}
	
	public List<Categoria> listar(){
		return categoriaRepository.findAll();
	}
	
	public Categoria findByCodigo(Long codigo) {
		return categoriaRepository.findOne(codigo);
	}
	
}


