package com.desafio.totvs.conta.application.service.mapper;

import com.desafio.totvs.conta.domain.model.Conta;
import com.desafio.totvs.conta.presentation.controller.dto.ContaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ContaMapper {
    ContaMapper INSTANCE = Mappers.getMapper(ContaMapper.class);

    ContaDTO toDto(Conta conta);
    Conta toEntity(ContaDTO contaDTO);
}
