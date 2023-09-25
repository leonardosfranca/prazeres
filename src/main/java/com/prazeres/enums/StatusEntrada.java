package com.prazeres.enums;

public enum StatusEntrada {
    EM_ANDAMENTO(1L, "EM ANDAMENTO"),
    FINALIZADA(2L, "FINALIZADA");

    private final Long value;
    private final String descricao;
    StatusEntrada(Long value, String descricao) {
        this.value = value;
        this.descricao = descricao;
    }

    public Long getValue() {
        return value;
    }
    public String getDescricao() {
        return descricao;
    }
}
