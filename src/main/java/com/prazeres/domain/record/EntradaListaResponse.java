package com.prazeres.domain.record;

import com.prazeres.domain.Quarto;
import com.prazeres.enums.StatusEntrada;
import com.prazeres.enums.StatusPagamento;

import java.time.LocalDate;
import java.time.LocalTime;

public record EntradaListaResponse(
        Long id,
        LocalDate dataRegistro,
        String placaVeiculo,
        Quarto quarto,
        LocalTime horarioEntrada,
        LocalTime horarioSaida,
        StatusEntrada statusEntrada,
        StatusPagamento statusPagamento

    ){
}
