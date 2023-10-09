package com.prazeres.domain.record;

import com.prazeres.enums.StatusQuarto;
import com.prazeres.enums.TipoQuarto;

public record QuartoResponse(
        Integer numero,
        String descricao,
        Integer capacidadePessoas,
        StatusQuarto statusQuarto,
        TipoQuarto tipoQuarto
    ){
}
