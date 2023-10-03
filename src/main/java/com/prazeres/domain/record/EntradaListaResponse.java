package com.prazeres.domain.record;

import com.prazeres.enums.StatusEntrada;
import com.prazeres.enums.StatusPagamento;

import java.time.LocalDate;
import java.time.LocalTime;

public record EntradaListaResponse(
        Long id,
        String placaVeiculo,
        Quarto quarto,
        LocalTime horarioEntrada,
        StatusEntrada statusEntrada,
        LocalDate dataRegistro,
        StatusPagamento statusPagamento

) {
    public record Quarto(
            Integer numero
    ) {
    }
}
