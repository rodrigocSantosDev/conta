package com.desafio.totvs.conta.domain.service;

import com.desafio.totvs.conta.application.service.ContaCsvService;
import com.desafio.totvs.conta.domain.model.Conta;
import com.desafio.totvs.conta.domain.service.ContaDomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ContaCsvServiceTest {

    @Mock
    private ContaDomainService contaDomainService;

    @InjectMocks
    private ContaCsvService contaCsvService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testImportCsv() throws IOException {
        String csvContent = "dataVencimento;dataPagamento;valor;descricao;situacao\n" +
                "01/01/2023;01/02/2023;100.00;Conta1;Pago\n" +
                "02/01/2023;;200.00;Conta2;Pendente";

        MultipartFile file = mock(MultipartFile.class);
        when(file.getInputStream()).thenReturn(new ByteArrayInputStream(csvContent.getBytes()));

        List<Conta> result = contaCsvService.importCsv(file);

        assertEquals(2, result.size());
        assertEquals(LocalDate.of(2023, 1, 1), result.get(0).getDataVencimento());
        assertEquals(LocalDate.of(2023, 1, 2), result.get(1).getDataVencimento());
        assertEquals(new BigDecimal("100.00"), result.get(0).getValor());
        assertEquals(new BigDecimal("200.00"), result.get(1).getValor());
        assertEquals("Conta1", result.get(0).getDescricao());
        assertEquals("Conta2", result.get(1).getDescricao());
        assertEquals("Pago", result.get(0).getSituacao());
        assertEquals("Pendente", result.get(1).getSituacao());

        verify(contaDomainService, times(1)).saveAll(anyList());
    }

    @Test
    public void testImportCsvWithInvalidData() throws IOException {
        String csvContent = "dataVencimento;dataPagamento;valor;descricao;situacao\n" +
                "invalid_date;01/02/2023;100.00;Conta1;Pago\n" +
                "02/01/2023;;invalid_value;Conta2;Pendente";

        MultipartFile file = mock(MultipartFile.class);
        when(file.getInputStream()).thenReturn(new ByteArrayInputStream(csvContent.getBytes()));

        List<Conta> result = contaCsvService.importCsv(file);

        assertTrue(result.isEmpty());
        verify(contaDomainService, never()).saveAll(anyList());
    }
}
