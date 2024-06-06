package com.desafio.totvs.conta.domain.repository;

import com.desafio.totvs.conta.domain.model.Conta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    Page<Conta> findAll(Pageable pageable);

    @Query("SELECT c FROM Conta c WHERE c.situacao = 'Pendente' AND c.dataVencimento BETWEEN :dataInicio AND :dataFim" +
            " AND (:descricao IS NULL OR c.descricao LIKE %:descricao%)")
    Page<Conta> findByDataVencimentoAndDescricao(
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            @Param("descricao") String descricao, Pageable pageable);

    @Query("SELECT SUM(c.valor) FROM Conta c WHERE c.situacao = 'Pago' AND c.dataPagamento BETWEEN :dataInicio AND :dataFim")
    BigDecimal findTotalPagoByPeriodo(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);

}