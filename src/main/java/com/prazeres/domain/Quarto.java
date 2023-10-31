package com.prazeres.domain;

import com.prazeres.enums.StatusQuarto;
import com.prazeres.enums.TipoQuarto;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Quarto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Integer numero;
    private Integer capacidadePessoas;
    @Enumerated(EnumType.STRING)
    private StatusQuarto statusQuarto;
    @Enumerated(EnumType.STRING)
    private TipoQuarto tipoQuarto;
    private Double valor;

    public Quarto() {
    }

    public Quarto(Long id, Integer numero, Integer capacidadePessoas, StatusQuarto statusQuarto,
                  TipoQuarto tipoQuarto, Double valor) {
        this.id = id;
        this.numero = numero;
        this.capacidadePessoas = capacidadePessoas;
        this.statusQuarto = statusQuarto;
        this.tipoQuarto = tipoQuarto;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public TipoQuarto getTipoQuarto() {
        return tipoQuarto;
    }

    public void setTipoQuarto(TipoQuarto tipoQuarto) {
        this.tipoQuarto = tipoQuarto;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
