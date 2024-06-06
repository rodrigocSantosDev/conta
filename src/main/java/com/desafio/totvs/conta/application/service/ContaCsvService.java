package com.desafio.totvs.conta.application.service;

import com.desafio.totvs.conta.domain.exception.InvalidDateFormatException;
import com.desafio.totvs.conta.domain.exception.InvalidValueFormatException;
import com.desafio.totvs.conta.domain.model.Conta;
import com.desafio.totvs.conta.domain.service.ContaDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class ContaCsvService {

    private final ContaDomainService contaDomainService;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ContaCsvService(ContaDomainService contaDomainService) {
        this.contaDomainService = contaDomainService;
    }

    public List<Conta> importCsv(MultipartFile file) {
        List<Conta> contas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            reader.readLine(); // Ignorar o cabeçalho

            reader.lines().forEach(line -> processLine(line, contas));

            if (!contas.isEmpty()) {
                contaDomainService.saveAll(contas);
                log.info("Todas as contas foram salvas com sucesso.");
            }

        } catch (Exception e) {
            log.error("Erro ao processar o arquivo CSV: {}", e.getMessage());
        }

        return contas;
    }

    private void processLine(String line, List<Conta> contas) {
        String[] fields = line.split(";");
        if (fields.length < 5) {
            log.info("Linha com número insuficiente de campos: {}", Arrays.toString(fields));
            return;
        }

        Conta conta = new Conta();
        try {
            conta.setDataVencimento(parseDate(fields[0]));
            conta.setDataPagamento(parseDateOrNull(fields[1]));
            conta.setValor(parseBigDecimal(fields[2]));
            conta.setDescricao(fields[3]);
            conta.setSituacao(fields[4]);
            contas.add(conta);
        } catch (InvalidDateFormatException | InvalidValueFormatException e) {
            log.error("Erro ao processar a linha: {} - erro: {}", line, e.getMessage());
        }
    }

    private LocalDate parseDateOrNull(String date) {
        return date.isEmpty() ? null : parseDate(date);
    }

    private LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException("Erro ao parsear a data: " + date);
        }
    }

    private BigDecimal parseBigDecimal(String value) {
        try {
            return value.isEmpty() ? BigDecimal.ZERO : new BigDecimal(value);
        } catch (NumberFormatException e) {
            throw new InvalidValueFormatException("Erro ao parsear o valor: " + value);
        }
    }
}
