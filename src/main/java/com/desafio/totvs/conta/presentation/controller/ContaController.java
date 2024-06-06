package com.desafio.totvs.conta.presentation.controller;

import com.desafio.totvs.conta.application.service.ContaCsvService;
import com.desafio.totvs.conta.application.service.ContaService;
import com.desafio.totvs.conta.application.service.mapper.ContaMapper;
import com.desafio.totvs.conta.domain.model.Conta;
import com.desafio.totvs.conta.presentation.controller.dto.ContaDTO;
import com.desafio.totvs.conta.presentation.controller.dto.SituacaoUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/conta")
public class ContaController {

    private final ContaService contaService;
    private final ContaCsvService contaCsvService;

    public ContaController(ContaService contaService, ContaCsvService contaCsvService) {
        this.contaService = contaService;
        this.contaCsvService = contaCsvService;
    }

    @GetMapping
    public Page<ContaDTO> getAllContas(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return contaService.getAllContas(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaDTO> getContaById(@PathVariable Long id) {
        ContaDTO contaDTO = contaService.getContaById(id);
        return ResponseEntity.ok(contaDTO);
    }

    @GetMapping("/pendente")
    public List<ContaDTO> getContasAPagarByFiltro(
            @RequestParam("dataInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInicio,
            @RequestParam("dataFim") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataFim,
            @RequestParam(required = false) String descricao,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return contaService.getContasAPagarByFiltro(dataInicio, dataFim, descricao, page, size);
    }

    @GetMapping("/total-pago")
    public ResponseEntity<BigDecimal> getTotalPagoPorPeriodo(
            @RequestParam("dataInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInicio,
            @RequestParam("dataFim") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataFim) {

        BigDecimal totalPago = contaService.getTotalPagoPorPeriodo(dataInicio, dataFim);
        return ResponseEntity.ok(totalPago);
    }

    @PostMapping
    public ContaDTO createOrUpdateConta(@RequestBody ContaDTO contaDTO) {
        return contaService.createOrUpdateConta(contaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaDTO> updateConta(@PathVariable Long id, @RequestBody ContaDTO contaDTO) {
        ContaDTO updatedContaDTO = contaService.updateConta(id, contaDTO);
        return ResponseEntity.ok(updatedContaDTO);
    }

    @PutMapping("/{id}/situacao")
    public ResponseEntity<ContaDTO> updateSituacao(
            @PathVariable Long id,
            @RequestBody SituacaoUpdateDTO situacaoUpdateDTO) {
        Optional<ContaDTO> updatedContaDTO = contaService.updateSituacao(id, situacaoUpdateDTO);
        return updatedContaDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/import")
    public ResponseEntity<List<ContaDTO>> importCsv(@RequestParam("file") MultipartFile file) {
        List<Conta> contas = contaCsvService.importCsv(file);
        List<ContaDTO> contaDTOs = contas.stream()
                .map(ContaMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(contaDTOs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConta(@PathVariable Long id) {
        contaService.deleteConta(id);
        return ResponseEntity.noContent().build();
    }
}
