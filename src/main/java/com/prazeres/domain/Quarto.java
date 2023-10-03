package com.prazeres.domain;

import com.prazeres.enums.StatusQuarto;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Quarto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private Integer capacidadePessoas;
    @Enumerated(EnumType.STRING)
    private StatusQuarto statusQuarto;

    public Quarto() {
    }

    public Quarto(Long id, Integer capacidadePessoas, StatusQuarto statusQuarto, String descricao) {
        this.id = id;
        this.capacidadePessoas = capacidadePessoas;
        this.descricao = descricao;
        this.statusQuarto = statusQuarto;
    }

    public Integer getCapacidadePessoas() {
        return capacidadePessoas;
    }

    public void setCapacidadePessoas(Integer capacidadePessoas) {
        this.capacidadePessoas = capacidadePessoas;
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

    public StatusQuarto getStatusQuarto() {
        return statusQuarto;
    }

    public void setStatusQuarto(StatusQuarto statusQuarto) {
        this.statusQuarto = statusQuarto;
    }
}
