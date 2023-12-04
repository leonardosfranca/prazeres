package com.prazeres.domain.record;

import com.prazeres.enums.StatusQuarto;
import com.prazeres.enums.TipoQuarto;

public record QuartoResponse(
        Long id,
        Integer numero,
        Integer capacidadePessoas,
        StatusQuarto statusQuarto,
        TipoQuarto tipoQuarto,
        TipoQuarto  valor
    ){
}
