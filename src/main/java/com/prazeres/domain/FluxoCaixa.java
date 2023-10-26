package com.prazeres.domain;

import com.prazeres.enums.TipoMovimentacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class FluxoCaixa implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate registroVenda;
    private String descricao;
    private TipoMovimentacao tipo;
    @NotBlank
    private Double valor;

    public FluxoCaixa() {
    }

    public FluxoCaixa(Long id, LocalDate registroVenda, String descricao, TipoMovimentacao tipo, Double valor) {
        this.id = id;
        this.registroVenda = registroVenda;
        this.descricao = descricao;
        this.tipo = tipo;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getRegistroVenda() {
        return registroVenda;
    }

    public void setRegistroVenda(LocalDate registroVenda) {
        this.registroVenda = registroVenda;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoMovimentacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimentacao tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
