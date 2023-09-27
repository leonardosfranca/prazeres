package com.prazeres.domain;

import com.prazeres.enums.StatusEntrada;
import com.prazeres.enums.StatusPagamento;
import com.prazeres.enums.StatusQuarto;
import com.prazeres.enums.TipoPagamento;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
public class Entrada implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String placaVeiculo;
    @OneToOne
    @JoinColumn(name = "quarto_id")
    private Quarto quarto;
    @OneToMany
    @JoinColumn(name = "itens_id")
    private List<Item> itensConsumo;
    private LocalTime horarioEntrada;
    private StatusEntrada statusEntrada;
    private LocalDate dataRegistro;
    private TipoPagamento tipoPagamento;
    private StatusPagamento statusPagamento;
    private BigDecimal valorTotal;
    private StatusQuarto statusQuarto;

    public Entrada() {
    }

    public Entrada(Long id, String placaVeiculo, Quarto quarto, List<Item> itensConsumo, LocalTime horarioEntrada,
                   StatusEntrada statusEntrada, LocalDate dataRegistro, TipoPagamento tipoPagamento,
                   StatusPagamento statusPagamento, BigDecimal valorTotal, StatusQuarto statusQuarto) {
        this.id = id;
        this.placaVeiculo = placaVeiculo;
        this.quarto = quarto;
        this.itensConsumo = itensConsumo;
        this.horarioEntrada = horarioEntrada;
        this.statusEntrada = statusEntrada;
        this.dataRegistro = dataRegistro;
        this.tipoPagamento = tipoPagamento;
        this.statusPagamento = statusPagamento;
        this.valorTotal = valorTotal;
        this.statusQuarto = statusQuarto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    public List<Item> getItensConsumo() {
        return itensConsumo;
    }

    public void setItensConsumo(List<Item> itensConsumo) {
        this.itensConsumo = itensConsumo;
    }

    public LocalTime getHorarioEntrada() {
        return horarioEntrada;
    }

    public void setHorarioEntrada(LocalTime horarioEntrada) {
        this.horarioEntrada = horarioEntrada;
    }

    public StatusEntrada getStatusEntrada() {
        return statusEntrada;
    }

    public void setStatusEntrada(StatusEntrada statusEntrada) {
        this.statusEntrada = statusEntrada;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public StatusPagamento getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(StatusPagamento statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public StatusQuarto getStatusQuarto() {
        return statusQuarto;
    }

    public void setStatusQuarto(StatusQuarto statusQuarto) {
        this.statusQuarto = statusQuarto;
    }
}
