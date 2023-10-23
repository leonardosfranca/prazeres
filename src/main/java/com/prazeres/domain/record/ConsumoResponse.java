package com.prazeres.domain.record;



public record ConsumoResponse(
        Entrada entrada,
        Integer quantidade,
        String descricao,
        Double valor
    ){

    public record  Entrada(
            Long id
    ){
    }
}
