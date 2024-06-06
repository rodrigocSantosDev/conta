package com.desafio.totvs.conta.application.service;

import com.desafio.totvs.conta.application.service.mapper.ContaMapper;
import com.desafio.totvs.conta.domain.exception.ContaNotFoundException;
import com.desafio.totvs.conta.domain.model.Conta;
import com.desafio.totvs.conta.domain.service.ContaDomainService;
import com.desafio.totvs.conta.presentation.controller.dto.ContaDTO;
import com.desafio.totvs.conta.presentation.controller.dto.SituacaoUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContaService {

    private final ContaDomainService contaDomainService;

    public ContaService(ContaDomainService contaDomainService) {
        this.contaDomainService = contaDomainService;
    }

    public Page<ContaDTO> getAllContas(Pageable pageable) {
        return contaDomainService.findAll(pageable).map(ContaMapper.INSTANCE::toDto);
    }

    public ContaDTO getContaById(Long id) {
        return contaDomainService.findById(id)
                .map(ContaMapper.INSTANCE::toDto)
                .orElseThrow(() -> new ContaNotFoundException("Conta não encontrada com id: " + id));
    }

    public ContaDTO createOrUpdateConta(ContaDTO contaDTO) {
        Conta conta = ContaMapper.INSTANCE.toEntity(contaDTO);
        Conta savedConta = contaDomainService.save(conta);
        return ContaMapper.INSTANCE.toDto(savedConta);
    }

    public ContaDTO updateConta(Long id, ContaDTO contaDTO) {
        Conta conta = contaDomainService.findById(id)
                .orElseThrow(() -> new ContaNotFoundException("Conta não encontrada com id: " + id));
        conta.setDescricao(contaDTO.getDescricao());
        conta.setDataVencimento(contaDTO.getDataVencimento());
        conta.setValor(contaDTO.getValor());
        conta.setSituacao(contaDTO.getSituacao());
        Conta updatedConta = contaDomainService.save(conta);
        return ContaMapper.INSTANCE.toDto(updatedConta);
    }


    public void deleteConta(Long id) {
        if (!contaDomainService.findById(id).isPresent()) {
            throw new ContaNotFoundException("Conta não encontrada com id: " + id);
        }
        contaDomainService.deleteById(id);
    }

    public Optional<ContaDTO> updateSituacao(Long id, SituacaoUpdateDTO situacaoDTO) {
        Conta conta = contaDomainService.findById(id)
                .orElseThrow(() -> new ContaNotFoundException("Conta não encontrada com id: " + id));
        conta.setSituacao(situacaoDTO.getSituacao());
        conta.setDataPagamento(situacaoDTO.getDataPagamento());
        Conta savedConta = contaDomainService.save(conta);
        return Optional.of(ContaMapper.INSTANCE.toDto(savedConta));
    }

    public List<ContaDTO> getContasAPagarByFiltro(LocalDate dataInicio, LocalDate dataFim, String descricao, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Conta> contas = contaDomainService.getContasAPagarByFiltro(dataInicio, dataFim, descricao, pageable);
        return contas.stream()
                .map(ContaMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public BigDecimal getTotalPagoPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        BigDecimal totalPago = contaDomainService.getTotalPagoPorPeriodo(dataInicio, dataFim);
        return Optional.ofNullable(totalPago).orElse(BigDecimal.ZERO);
    }

}
