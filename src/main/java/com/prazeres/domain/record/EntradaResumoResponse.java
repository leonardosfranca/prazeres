package com.prazeres.domain.record;

import com.prazeres.enums.StatusEntrada;
import com.prazeres.enums.StatusPagamento;
import com.prazeres.enums.StatusQuarto;
import com.prazeres.enums.TipoQuarto;

import java.time.LocalDate;
import java.time.LocalTime;

public record EntradaResumoResponse(
        Long id,
        LocalDate dataRegistro,
        String placaVeiculo,
        Quarto quarto,
        LocalTime horarioEntrada,
        LocalTime horarioSaida,
        StatusEntrada statusEntrada,
        StatusPagamento statusPagamento

    ){
    public record Quarto(
            StatusQuarto statusQuarto,
            TipoQuarto tipoQuarto
    ){}
}
