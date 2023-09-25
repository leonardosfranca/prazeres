package com.prazeres.enums;

public enum StatusPagamento {
    PAGO(1L, "PAGO"),
    PENDENTE(2L, "PENDETE"),
    FALHA_NO_PAGAMENTO(3L, "FALHA NO PAGAMENTO"),
    CANCELADO(4L, "CANCELADO"),
    PAGAMENTO_PARCIAL(5L, "PAGAMENTO PARCIAL");

    private final Long value;
    private final String descricao;
    StatusPagamento(Long value, String descricao) {
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
