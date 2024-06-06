package com.desafio.totvs.conta.presentation.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SituacaoUpdateDTO {
    private String situacao;
    private LocalDate dataPagamento;

}

