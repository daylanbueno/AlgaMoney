package com.example.algamoney.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.PessoaRepository;
import com.example.algamoney.api.repository.filter.LancamentoFilter;
import com.example.algamoney.api.repository.LancamentoRepository;
import com.example.algamoney.api.service.exception.PessoaInativaOuNaoExiste;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository; 
	
	@Autowired
	private PessoaRepository pessoaRepository;

	public Lancamento salvar(Lancamento lancamento) {
		Pessoa pessoa = pessoaRepository.findOne(lancamento.getPessoa().getCodigo());
		if(pessoa == null || !pessoa.isAtivo()) {
			throw new PessoaInativaOuNaoExiste();
		}
		return lancamentoRepository.save(lancamento);
	}

	public List<Lancamento> listar(){
		return lancamentoRepository.findAll();
	}
	
	public Lancamento buscarPorCodigo(Long codigo) {
		return lancamentoRepository.findOne(codigo);
	}

	public List<Lancamento> pesquisar(LancamentoFilter lancamentoFilter){
		return lancamentoRepository.filtrar(lancamentoFilter);
	}
	
	public void remover(Long codigo) {
		lancamentoRepository.delete(codigo);
	}
	
	
}
