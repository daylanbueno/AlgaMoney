package com.example.algamoney.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.repository.lancamentoRepository;

@Service
public class LancamentoService {

	@Autowired
	private lancamentoRepository lancamentoRepository; 


	public List<Lancamento> listar(){
		return lancamentoRepository.findAll();
	}
	
	public Lancamento buscarPorCodigo(Long codigo) {
		return lancamentoRepository.findOne(codigo);
	}
	
}
