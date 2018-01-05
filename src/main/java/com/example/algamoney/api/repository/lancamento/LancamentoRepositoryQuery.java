package com.example.algamoney.api.repository.lancamento;

import com.example.algamoney.api.repository.filter.LancamentoFilter;

import java.util.List;

import com.example.algamoney.api.model.Lancamento;

public interface LancamentoRepositoryQuery{
	
	public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);
}
