package com.prazeres.domain.record;

import com.prazeres.enums.StatusEntrada;
import com.prazeres.enums.StatusPagamento;
import com.prazeres.enums.TipoPagamento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record EntradaResponse(
        Quarto quarto,
        LocalDate dataRegistro,
        LocalTime horarioEntrada,
        StatusEntrada statusEntrada,
        TipoPagamento tipoPagamento,
        StatusPagamento statusPagamento,
        List<ConsumoResponse> consumo,
        Double valorEntrada,
        Double totalConsumo,
        Double valorTotal

        ) {
    public record Quarto(
            Integer numero
    ) {}
}
