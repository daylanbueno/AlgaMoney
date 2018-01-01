package com.example.algamoney.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.ropository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Pessoa atualizar(Long codigo,Pessoa pessoa) {
		Pessoa pessoaCadastrada = pessoaRepository.findOne(codigo);
		if (pessoaCadastrada == null) {
			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties(pessoa, pessoaCadastrada, "codigo");
		pessoaRepository.save(pessoaCadastrada);
		return pessoaCadastrada;
	}
}
