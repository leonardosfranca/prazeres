package com.prazeres.domain.record;

import com.prazeres.domain.Consumo;
import com.prazeres.domain.Quarto;
import com.prazeres.enums.StatusEntrada;

import java.time.LocalTime;
import java.util.List;

public record EntradaBuscaIdResponse(
        Quarto quarto,
        LocalTime horaEntrada,
        LocalTime horaSaida,
        String placaVeiculo,
        LocalTime tempoPermanecido,
        List<ConsumoResponse> consumo,
        StatusEntrada statusEntrada,
        Double totalConsumo,
        Double valorEntrada,
        Double total
) {
    public record Quarto(
            Integer numero
    ) {}

}
