package com.prazeres.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class FluxoCaixa implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime registroVenda;
    private String descricao;
    @ManyToOne
    private Quarto quarto;
    private BigDecimal valorEntrada;
    private BigDecimal valorSaida;
    private BigDecimal valorTotal;

    public FluxoCaixa() {
    }

    public FluxoCaixa(Long id, LocalDateTime registroVenda, String descricao,
                      Quarto quarto, BigDecimal valorEntrada, BigDecimal valorSaida, BigDecimal valorTotal) {
        this.id = id;
        this.registroVenda = registroVenda;
        this.descricao = descricao;
        this.quarto = quarto;
        this.valorEntrada = valorEntrada;
        this.valorSaida = valorSaida;
        this.valorTotal = valorTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getRegistroVenda() {
        return registroVenda;
    }

    public void setRegistroVenda(LocalDateTime registroVenda) {
        this.registroVenda = registroVenda;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    public BigDecimal getValorEntrada() {
        return valorEntrada;
    }

    public void setValorEntrada(BigDecimal valorEntrada) {
        this.valorEntrada = valorEntrada;
    }

    public BigDecimal getValorSaida() {
        return valorSaida;
    }

    public void setValorSaida(BigDecimal valorSaida) {
        this.valorSaida = valorSaida;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
