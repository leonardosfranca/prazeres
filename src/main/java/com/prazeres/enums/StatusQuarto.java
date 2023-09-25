package com.prazeres.enums;

public enum StatusQuarto {
    LIBERADO(1L, "lIBERADO"),
    OCUPADO(2L, "OCUPADO");


    private final Long value;
    private final String descricao;
    StatusQuarto(Long value, String descricao) {
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
