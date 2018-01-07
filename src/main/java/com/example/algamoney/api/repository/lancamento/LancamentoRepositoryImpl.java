package com.example.algamoney.api.repository.lancamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.Lancamento_;
import com.example.algamoney.api.repository.filter.LancamentoFilter;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext // injeção do entitymanager do jpa.
	private EntityManager manager; //

	@Override
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageAble) {

		CriteriaBuilder builder = manager.getCriteriaBuilder(); // criteria do jpa. // criteria do hibernat depreciou.
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);

		Root<Lancamento> root = criteria.from(Lancamento.class);

		// criando restrições
		Predicate[] predicates = criateRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<Lancamento> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query,pageAble);
		
		return new  PageImpl<>(query.getResultList(), pageAble, total(lancamentoFilter));
	}

	
	private Predicate[] criateRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder,Root<Lancamento> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (lancamentoFilter.getDescricao() != null) {
			predicates.add(builder.like(builder.lower(root.get(Lancamento_.descricao)),"%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));	
		}

		if (lancamentoFilter.getDataVencimentoDe() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), lancamentoFilter.getDataVencimentoDe()));
		}
		
		if(lancamentoFilter.getDataVencimentoAte() !=null) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), lancamentoFilter.getDataVencimentoAte()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<Lancamento> query, Pageable pageAble) {
		int paginaAtual = pageAble.getPageNumber();
		int totalRegistroPorPagina = pageAble.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistroPorPagina;
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistroPorPagina);
	}
	

	private Long total(LancamentoFilter lancamentoFilter) {
		CriteriaBuilder builder =  manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		Predicate predicates[] = criateRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates); // adicionando restriçoes criada no método acima.
		criteria.select(builder.count(root)); // Quantos registro tem ae?
		return manager.createQuery(criteria).getSingleResult();
	}
}
