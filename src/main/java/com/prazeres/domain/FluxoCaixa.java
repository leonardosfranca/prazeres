package com.prazeres.domain;

import jakarta.persistence.*;

import java.io.Serializable;
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
    private Double valorEntrada;
    private Double valorSaida;
    private Double valorTotal;

    public FluxoCaixa() {
    }

    public FluxoCaixa(Long id, LocalDateTime registroVenda, String descricao,
                      Quarto quarto, Double valorEntrada, Double valorSaida, Double valorTotal) {
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

    public Double getValorEntrada() {
        return valorEntrada;
    }

    public void setValorEntrada(Double valorEntrada) {
        this.valorEntrada = valorEntrada;
    }

    public Double getValorSaida() {
        return valorSaida;
    }

    public void setValorSaida(Double valorSaida) {
        this.valorSaida = valorSaida;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
