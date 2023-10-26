package com.prazeres.domain.record;



public record ConsumoResponse(
        Long id,
        Integer quantidade,
        String descricao,
        Double valor
    ){}
