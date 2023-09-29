package com.prazeres.domain;

import com.prazeres.enums.StatusEntrada;
import com.prazeres.enums.StatusPagamento;
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
    @ManyToOne
    @JoinColumn(name = "quarto_id")
    private Quarto quarto;
    @OneToMany
    @JoinColumn(name = "consumo_id")
    private List<Consumo> consumos;
    private LocalTime horarioEntrada;
    private StatusEntrada statusEntrada;
    private LocalDate dataRegistro;
    private TipoPagamento tipoPagamento;
    private StatusPagamento statusPagamento;
    @Column(name = "valor_parcial")
    private BigDecimal valorTotal;

    public Entrada() {
    }

    public Entrada(Long id, String placaVeiculo, Quarto quarto, List<Consumo> consumos, LocalTime horarioEntrada,
                   StatusEntrada statusEntrada, LocalDate dataRegistro, TipoPagamento tipoPagamento,
                   StatusPagamento statusPagamento, BigDecimal valorTotal) {
        this.id = id;
        this.placaVeiculo = placaVeiculo;
        this.quarto = quarto;
        this.consumos = consumos;
        this.horarioEntrada = horarioEntrada;
        this.statusEntrada = statusEntrada;
        this.dataRegistro = dataRegistro;
        this.tipoPagamento = tipoPagamento;
        this.statusPagamento = statusPagamento;
        this.valorTotal = valorTotal;
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

    public List<Consumo> getConsumos() {
        return consumos;
    }

    public void setConsumos(List<Consumo> consumos) {
        this.consumos = consumos;
    }
}
