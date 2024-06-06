package com.desafio.totvs.conta.domain.service;

import com.desafio.totvs.conta.domain.model.Conta;
import com.desafio.totvs.conta.domain.repository.ContaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ContaDomainService {

    private final ContaRepository contaRepository;

    public ContaDomainService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    public Optional<Conta> findById(Long id) {
        return contaRepository.findById(id);
    }

    public Conta save(Conta conta) {
        return contaRepository.save(conta);
    }

    public List<Conta> saveAll(List<Conta> contas) {
        return contaRepository.saveAll(contas);
    }

    public List<Conta> findAll() {
        return contaRepository.findAll();
    }

    public Page<Conta> findAll(Pageable pageable) {
        return contaRepository.findAll(pageable);
    }

    public void deleteById(Long id) {
        contaRepository.deleteById(id);
    }

    public Page<Conta> getContasAPagarByFiltro(LocalDate dataInicio, LocalDate dataFim, String descricao,Pageable pageable) {
        return contaRepository.findByDataVencimentoAndDescricao(dataInicio, dataFim, descricao,pageable);
    }

    public BigDecimal getTotalPagoPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return contaRepository.findTotalPagoByPeriodo(dataInicio, dataFim);
    }
}

