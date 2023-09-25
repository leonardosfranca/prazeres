package com.prazeres.domain;

import com.prazeres.enums.StatusQuarto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class Quarto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer numero;
    private Integer capacidadePessoas;
    private StatusQuarto statusQuarto;

    public Quarto() {
    }

    public Quarto(Integer numero, Integer capacidadePessoas, StatusQuarto statusQuarto) {
        this.numero = numero;
        this.capacidadePessoas = capacidadePessoas;
        this.statusQuarto = statusQuarto;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getCapacidadePessoas() {
        return capacidadePessoas;
    }

    public void setCapacidadePessoas(Integer capacidadePessoas) {
        this.capacidadePessoas = capacidadePessoas;
    }

    public StatusQuarto getStatusQuarto() {
        return statusQuarto;
    }

    public void setStatusQuarto(StatusQuarto statusQuarto) {
        this.statusQuarto = statusQuarto;
    }
}
