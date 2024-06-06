package com.desafio.totvs.conta.domain.service;

import com.desafio.totvs.conta.application.service.ContaService;
import com.desafio.totvs.conta.domain.model.Conta;
import com.desafio.totvs.conta.domain.service.ContaDomainService;
import com.desafio.totvs.conta.presentation.controller.dto.ContaDTO;
import com.desafio.totvs.conta.presentation.controller.dto.SituacaoUpdateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ContaServiceTest {

    @Mock
    private ContaDomainService contaDomainService;

    @InjectMocks
    private ContaService contaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllContas() {
        Conta conta = new Conta();
        Page<Conta> page = new PageImpl<>(Arrays.asList(conta));
        Pageable pageable = PageRequest.of(0, 10);

        when(contaDomainService.findAll(pageable)).thenReturn(page);

        Page<ContaDTO> result = contaService.getAllContas(pageable);

        assertEquals(1, result.getContent().size());
        verify(contaDomainService, times(1)).findAll(pageable);
    }

    @Test
    public void testGetContaById() {
        Conta conta = new Conta();
        conta.setId(1L);
        when(contaDomainService.findById(1L)).thenReturn(Optional.of(conta));

        ContaDTO result = contaService.getContaById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(contaDomainService, times(1)).findById(1L);
    }

    @Test
    public void testCreateOrUpdateConta() {
        Conta conta = new Conta();
        ContaDTO contaDTO = new ContaDTO();
        when(contaDomainService.save(any(Conta.class))).thenReturn(conta);

        ContaDTO result = contaService.createOrUpdateConta(contaDTO);

        assertNotNull(result);
        verify(contaDomainService, times(1)).save(any(Conta.class));
    }

    @Test
    public void testDeleteConta() {
        when(contaDomainService.findById(any())).thenReturn(Optional.of(new Conta()));

        contaService.deleteConta(1L);

        verify(contaDomainService, times(1)).deleteById(1L);
    }

    @Test
    public void testUpdateSituacao() {
        Conta conta = new Conta();
        conta.setId(1L);
        conta.setSituacao("Pendente");
        SituacaoUpdateDTO situacaoDTO = new SituacaoUpdateDTO();
        situacaoDTO.setSituacao("Pago");

        when(contaDomainService.findById(1L)).thenReturn(Optional.of(conta));
        when(contaDomainService.save(any(Conta.class))).thenReturn(conta);

        Optional<ContaDTO> result = contaService.updateSituacao(1L, situacaoDTO);

        assertTrue(result.isPresent());
        assertEquals("Pago", result.get().getSituacao());
        verify(contaDomainService, times(1)).findById(1L);
        verify(contaDomainService, times(1)).save(any(Conta.class));
    }

    @Test
    public void testGetTotalPagoPorPeriodo() {
        LocalDate dataInicio = LocalDate.of(2023, 1, 1);
        LocalDate dataFim = LocalDate.of(2023, 12, 31);
        BigDecimal totalPago = BigDecimal.valueOf(1000);

        when(contaDomainService.getTotalPagoPorPeriodo(dataInicio, dataFim)).thenReturn(totalPago);

        BigDecimal result = contaService.getTotalPagoPorPeriodo(dataInicio, dataFim);

        assertEquals(totalPago, result);
        verify(contaDomainService, times(1)).getTotalPagoPorPeriodo(dataInicio, dataFim);
    }
}
