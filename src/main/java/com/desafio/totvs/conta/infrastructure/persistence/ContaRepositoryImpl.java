package com.desafio.totvs.conta.infrastructure.persistence;

import com.desafio.totvs.conta.domain.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepositoryImpl extends JpaRepository<Conta, Long> {

}
