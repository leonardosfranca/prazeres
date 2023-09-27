package com.prazeres.enums;

public enum StatusQuarto {
    LIBERADO("lIBERADO"),
    OCUPADO( "OCUPADO");


    private final String descricao;
    StatusQuarto(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }


}
