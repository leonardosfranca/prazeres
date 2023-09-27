package com.prazeres.enums;

public enum StatusPagamento {
    PAGO("PAGO"),
    PENDENTE("PENDETE"),
    FALHA_NO_PAGAMENTO( "FALHA NO PAGAMENTO"),
    CANCELADO("CANCELADO"),
    PAGAMENTO_PARCIAL("PAGAMENTO PARCIAL");


    private final String descricao;
    StatusPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
