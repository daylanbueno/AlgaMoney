package com.example.algamoney.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.PessoaRepository;


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
	
	public Pessoa findPessoaByCodigo(Long codigo) {
		return pessoaRepository.findOne(codigo);
	}
	
	public void atualizaStatus(Long codigo, Boolean ativo) {
		Pessoa pessoaRetornada = findPessoaByCodigo(codigo);
		if(pessoaRetornada == null) {
			throw new EmptyResultDataAccessException(1);
		}
		pessoaRetornada.setAtivo(ativo);
		pessoaRepository.save(pessoaRetornada);
	}
	
	public void deletar(Long codigo) {
		pessoaRepository.delete(codigo);
	}
	
	public Pessoa salvar(Pessoa pessoa) {
		return pessoaRepository.save(pessoa);
	}
	
	public List<Pessoa> listaPessoa(){
		return pessoaRepository.findAll();
	}
	
	
}
