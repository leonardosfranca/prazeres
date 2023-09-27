package com.prazeres.enums;

public enum StatusEntrada {
    EM_ANDAMENTO("EM ANDAMENTO"),
    FINALIZADA("FINALIZADA");


    private final String descricao;
    StatusEntrada(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
