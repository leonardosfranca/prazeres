package com.prazeres.domain;

import com.prazeres.enums.StatusEntrada;
import com.prazeres.enums.StatusPagamento;
import com.prazeres.enums.TipoPagamento;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Entrada implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String placa;
    @OneToOne
    private Quarto quarto;
    private BigDecimal valorTotal;
    private StatusPagamento statusPagamento;
    private StatusEntrada statusEntrada;
    private TipoPagamento tipoPagamento;
    private Long itensConsumo;

    public Entrada() {
    }
    public Entrada(Long id, String placa, LocalTime registro, LocalDate data, BigDecimal valorTotal, StatusPagamento statusPagamento,
                   StatusEntrada statusEntrada, TipoPagamento tipoPagamento, Long itensConsumo) {
        this.id = id;
        this.placa = placa;
        this.valorTotal = valorTotal;
        this.statusPagamento = statusPagamento;
        this.statusEntrada = statusEntrada;
        this.tipoPagamento = tipoPagamento;
        this.itensConsumo = itensConsumo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Long getItensConsumo() {
        return itensConsumo;
    }

    public void setItensConsumo(Long itensConsumo) {
        this.itensConsumo = itensConsumo;
    }

    public StatusPagamento getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(StatusPagamento statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public StatusEntrada getStatusEntrada() {
        return statusEntrada;
    }

    public void setStatusEntrada(StatusEntrada statusEntrada) {
        this.statusEntrada = statusEntrada;
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }


    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }
}
