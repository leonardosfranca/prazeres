package com.prazeres.enums;

public enum TipoPagamento {
    DINHEIRO(1L, "DINHEIRO"),
    CARTAO_CREDITO(2L, "CARTÃO CRÉDITO"),
    CARTAO_DEBITO(3L, "CARTÃO DÉBITO"),
    PIX(4L, "PIX");

    private final Long value;
    private final String descricao;
    TipoPagamento(Long value, String descricao) {
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
