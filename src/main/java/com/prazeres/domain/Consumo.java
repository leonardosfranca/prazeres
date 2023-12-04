package com.prazeres.domain;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Consumo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantidade;
    private Double valor;
    @OneToOne
    @JoinColumn(name = "entrada_id")
    private Entrada entrada;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    private Double valorTotal;

    public Consumo() {
    }

    public Consumo(Long id, Integer quantidade, Double valor, Entrada entrada, Item item, Double valorTotal) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
        this.entrada = entrada;
        this.item = item;
        this.valorTotal = valorTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Entrada getEntrada() {
        return entrada;
    }

    public void setEntrada(Entrada entrada) {
        this.entrada = entrada;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
