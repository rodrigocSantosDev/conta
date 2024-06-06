package com.desafio.totvs.conta.presentation.controller;

import com.desafio.totvs.conta.application.service.ContaService;
import com.desafio.totvs.conta.application.service.ContaCsvService;
import com.desafio.totvs.conta.presentation.controller.ContaController;
import com.desafio.totvs.conta.presentation.controller.dto.ContaDTO;
import com.desafio.totvs.conta.presentation.controller.dto.SituacaoUpdateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ContaControllerTest {

    @Mock
    private ContaService contaService;

    @Mock
    private ContaCsvService contaCsvService;

    @InjectMocks
    private ContaController contaController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(contaController).build();
    }

    @Test
    public void testGetContaById() throws Exception {
        ContaDTO contaDTO = new ContaDTO();
        contaDTO.setId(1L);

        when(contaService.getContaById(1L)).thenReturn(contaDTO);

        mockMvc.perform(get("/api/conta/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testCreateConta() throws Exception {
        ContaDTO contaDTO = new ContaDTO();
        contaDTO.setId(1L);

        when(contaService.createOrUpdateConta(any(ContaDTO.class))).thenReturn(contaDTO);

        mockMvc.perform(post("/api/conta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\":\"Conta Teste\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testImportCsv() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "data".getBytes());

        when(contaCsvService.importCsv(any(MockMultipartFile.class))).thenReturn(Collections.emptyList());

        mockMvc.perform(multipart("/api/conta/import").file(file))
                .andExpect(status().isOk());
    }
}


