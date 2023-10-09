package com.prazeres.domain;

import com.prazeres.domain.record.EntradaListaResponse;
import com.prazeres.enums.StatusQuarto;
import com.prazeres.enums.TipoQuarto;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Quarto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer numero;
    private String descricao;
    private Integer capacidadePessoas;
    @Enumerated(EnumType.STRING)
    private StatusQuarto statusQuarto;
    @Enumerated(EnumType.STRING)
    private TipoQuarto tipoQuarto;

    public Quarto() {
    }
    public Quarto(Long id, String descricao, Integer capacidadePessoas, StatusQuarto statusQuarto, TipoQuarto tipoQuarto) {
    }

    public Quarto(Long id, Integer numero, String descricao, Integer capacidadePessoas, StatusQuarto statusQuarto,
                  TipoQuarto tipoQuarto) {
        this.id = id;
        this.numero = numero;
        this.descricao = descricao;
        this.capacidadePessoas = capacidadePessoas;
        this.statusQuarto = statusQuarto;
        this.tipoQuarto = tipoQuarto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
}
