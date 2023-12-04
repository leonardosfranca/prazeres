package com.prazeres.domain;

import jakarta.persistence.*;
import org.springframework.http.ProblemDetail;

import java.io.Serializable;

@Entity
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private Double valor;
    @ManyToOne
    private Quarto quarto;
    public Item() {
    }

    public Item(Long id, String descricao, Double valor, Quarto quarto) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.quarto = quarto;
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }
}
