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
    private Double subTotal;

    public Consumo() {
    }

    public Consumo(Long id, Integer quantidade, Item item, Entrada entrada, Double subTotal) {
        this.id = id;
        this.quantidade = quantidade;
        this.item = item;
        this.entrada = entrada;
        this.subTotal = subTotal;
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

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }
}
