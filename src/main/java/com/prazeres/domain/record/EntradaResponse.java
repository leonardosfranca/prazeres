package com.prazeres.domain.record;

import com.prazeres.enums.StatusEntrada;
import com.prazeres.enums.StatusPagamento;
import com.prazeres.enums.TipoPagamento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record EntradaResponse(
        String placaVeiculo,
        Quarto quarto,
        List<ConsumoResumoResponse> consumo,
        LocalTime horarioEntrada,
        StatusEntrada statusEntrada,
        LocalDate dataRegistro,
        TipoPagamento tipoPagamento,
        StatusPagamento statusPagamento

        ) {
    public record Quarto(
            Integer numero
    ) {}

    public record Consumo(
            String descricao,
            Double valor
    ) {}
}
