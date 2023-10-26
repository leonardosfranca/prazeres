package com.prazeres.domain.record;



public record ConsumoResponse(
        Integer quantidade,
        String descricao,
        Double valor
        //Double totalConsumo
    ){}
