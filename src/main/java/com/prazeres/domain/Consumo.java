package com.prazeres.domain;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Consumo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantidade;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    @OneToOne
    @JoinColumn(name = "entrada_id")
    private Entrada entrada;
    private Double valor;
    private Double valorTotal;

    public Consumo() {
    }

    public Consumo(Long id, Integer quantidade, Item item, Entrada entrada, Double valor, Double valorTotal) {
        this.id = id;
        this.quantidade = quantidade;
        this.item = item;
        this.entrada = entrada;
        this.valor = valor;
        this.valorTotal = valorTotal;
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Entrada getEntrada() {
        return entrada;
    }

    public void setEntrada(Entrada entrada) {
        this.entrada = entrada;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double subTotal) {
        this.valor = subTotal;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
