package com.example.algamoney.api.ropository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.algamoney.api.model.Pessoa;

public interface  PessoaRepository extends JpaRepository<Pessoa,Long>{

}
