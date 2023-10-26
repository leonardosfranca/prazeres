package com.prazeres.services;

import com.prazeres.domain.Entrada;
import com.prazeres.domain.FluxoCaixa;
import com.prazeres.domain.Quarto;
import com.prazeres.domain.exceptionhandler.EntidadeNaoEncontradaException;
import com.prazeres.domain.exceptionhandler.NegocioException;
import com.prazeres.domain.record.ConsumoResponse;
import com.prazeres.domain.record.EntradaResponse;
import com.prazeres.domain.record.EntradaResumoResponse;
import com.prazeres.enums.*;
import com.prazeres.repositories.ConsumoRepository;
import com.prazeres.repositories.EntradaRepository;
import com.prazeres.repositories.FluxoCaixaRepository;
import com.prazeres.repositories.QuartoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EntradaService {

    private final EntradaRepository entradaRepository;
    private final QuartoRepository quartoRepository;
    private final ConsumoService consumoService;
    private final ConsumoRepository consumoRepository;
    private final FluxoCaixaRepository fluxoCaixaRepository;

    public EntradaService(EntradaRepository entradaRepository, QuartoRepository quartoRepository,
                          ConsumoService consumoService, ConsumoRepository consumoRepository,
                          FluxoCaixaRepository fluxoCaixaRepository) {
        this.entradaRepository = entradaRepository;
        this.quartoRepository = quartoRepository;
        this.consumoService = consumoService;
        this.consumoRepository = consumoRepository;
        this.fluxoCaixaRepository = fluxoCaixaRepository;
    }

    public List<EntradaResumoResponse> findAll() {
        var entrada = entradaRepository.findAll();
        List<EntradaResumoResponse> entradaResumoResponseList = new ArrayList<>();
        entrada.forEach(entradas -> {
            EntradaResumoResponse entradaResumoResponse = new EntradaResumoResponse(
                    entradas.getId(),
                    entradas.getDataRegistro(),
                    entradas.getPlacaVeiculo(),
                    new EntradaResumoResponse.Quarto(entradas.getQuarto().getStatusQuarto(), entradas.getQuarto().getTipoQuarto()),
                    entradas.getHorarioEntrada(),
                    entradas.getHorarioSaida(),
                    entradas.getStatusEntrada(),
                    entradas.getStatusPagamento());
            entradaResumoResponseList.add(entradaResumoResponse);
        });
        return entradaResumoResponseList;
    }

    public Entrada buscaEntradaId(Long entradaId) {
        return entradaRepository.findById(entradaId)
                .orElseThrow(()-> new EntidadeNaoEncontradaException("Entrada não encontrada"));
    }

    public List<Entrada> listarPorStatusEntrada(StatusEntrada statusEntrada) {
        return entradaRepository.findAllByStatusEntrada(statusEntrada);
    }

    public List<Entrada> listarPorDataAtual() {
        LocalDate hoje = LocalDate.now();
        return entradaRepository.findAllByDataRegistro(hoje);
    }

    public List<Entrada> listarPorDataRegistro(LocalDate dataRegistro) {
        List<Entrada> entradas = entradaRepository.findAllByDataRegistro(dataRegistro);

        if (entradas.isEmpty()) {
            throw new NegocioException("Nenhum registro para essa data");
        }
        return entradas;
    }

    public EntradaResponse buscar(Long entradaId) {
        Entrada entrada = entradaRepository.findById(entradaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Entrada não encontrada"));

        List<ConsumoResponse> consumoResponseList = new ArrayList<>();

        var consumos = consumoRepository.findAllByEntrada_Id(entradaId);

        consumos.forEach(consumo -> {
            ConsumoResponse consumoResponse = new ConsumoResponse(
                    consumo.getQuantidade(),
            consumo.getItem().getDescricao(),
            consumo.getValor());
            consumoResponseList.add(consumoResponse);
        });

        var totalConsumo = consumoRepository.valorConsumo(entradaId);
        var valor = totalConsumo + entrada.getValorEntrada();
        return new EntradaResponse(
                new EntradaResponse.Quarto(
                        entrada.getQuarto().getCapacidadePessoas()),
                        entrada.getDataRegistro(),
                        entrada.getHorarioEntrada(),
                        entrada.getStatusEntrada(),
                        entrada.getTipoPagamento(),
                        entrada.getStatusPagamento(),
                        consumoResponseList,
                        entrada.getValorEntrada(),
                        totalConsumo.doubleValue(),
                        valor
        );
    }

    public Entrada salvaEntrada(Entrada entrada) {
        Quarto quarto = quartoRepository.findById(entrada.getQuarto().getId())
                .orElseThrow(() -> new NegocioException("Quarto não encontrado"));

        if (quarto.getStatusQuarto().equals(StatusQuarto.OCUPADO)) {
            throw new NegocioException("Quarto ocupado");
        }
        salvaEntrada(entrada, quarto);
        tipoQuarto(entrada);

        var entradaRepositorio = entradaRepository.save(entrada);
        quartoRepository.save(quarto);
        return entradaRepositorio;
    }

    private void tipoQuarto(Entrada entrada) {
        switch (entrada.getQuarto().getTipoQuarto()) {
            case SUITE_MASTER -> entrada.setValorEntrada(70D);
            case SUITE_COMUM -> entrada.setValorEntrada(40D);
            case SUITE_VIP -> entrada.setValorEntrada(50D);
            case SUITE_EXECUTIVA -> entrada.setValorEntrada(60D);
        }
    }

    private void salvaEntrada(Entrada entrada, Quarto quarto) {
        entrada.setHorarioEntrada(LocalTime.now());
        entrada.setDataRegistro(LocalDate.now());
        entrada.setStatusEntrada(StatusEntrada.EM_ANDAMENTO);
        entrada.setStatusPagamento(StatusPagamento.A_PAGAR);
        quarto.setStatusQuarto(StatusQuarto.OCUPADO);
        entrada.setTipoPagamento(TipoPagamento.PENDENTE);
    }

    public Entrada atualizar(Long entradaId, Entrada entradaRequest) {
        Entrada entrada = entradaRepository.findById(entradaId)
                .orElseThrow(() -> new NegocioException("Entrada não encontrada"));

        entrada.setPlacaVeiculo(entradaRequest.getPlacaVeiculo());
        entrada.setStatusEntrada(entradaRequest.getStatusEntrada());
        entrada.setTipoPagamento(entradaRequest.getTipoPagamento());
        entrada.setStatusPagamento(entradaRequest.getStatusPagamento());


        if (!entradaRequest.getTipoPagamento().equals(TipoPagamento.PENDENTE)) {
            validacaoHorario(entrada);

            if (entradaRequest.getStatusPagamento().equals(StatusPagamento.FINALIZADO)) {
                FluxoCaixa fluxoCaixa = new FluxoCaixa();
                fluxoCaixa.setRegistroVenda(LocalDate.now());
                var relatorio = validacaoRelatorio();

                double valorTotal = fluxoCaixaRepository.valorCaixa() + entrada.getValorEntrada();
                fluxoCaixa.setDescricao(relatorio);
                fluxoCaixa.setTipo(TipoMovimentacao.ENTRADA);
                fluxoCaixa.setTipo(TipoMovimentacao.SAIDA);
                fluxoCaixa.setValor(valorTotal);

                fluxoCaixaRepository.save(fluxoCaixa);
                entradaRepository.save(entrada);
            }
        }
        return entrada;
    }

    private String validacaoRelatorio() {
        LocalTime noite = LocalTime.of(18, 0);
        LocalTime dia = LocalTime.of(6, 0);

        String relatorio;
        if (LocalTime.now().isBefore(noite) && LocalTime.now().isAfter(dia)) {
            relatorio = "Entrada dia!";
        } else {
            relatorio = "Entrada noite!";
        }
        return relatorio;
    }

    private void validacaoHorario(Entrada entrada) {
        LocalTime horarioEntrada = entrada.getHorarioEntrada();
        LocalTime horarioSaida = LocalTime.now();
        entrada.setHorarioSaida(horarioSaida);

        Duration tempoPermanecido = Duration.between(horarioEntrada, horarioSaida);
        long minutosTotais = tempoPermanecido.toMinutes();
        double taxaCustoAdicionalPorHora = 5.0;

        double custoAdicional = 0D;

        if (minutosTotais > 120) {
            custoAdicional = Math.ceil(minutosTotais / 60.0) * taxaCustoAdicionalPorHora;
        }
        double valorTotal = entrada.getValorEntrada() + custoAdicional;
        entrada.setValorEntrada(valorTotal);

        float totalConsumos = consumoRepository.valorConsumo(entrada.getId());

        double total = valorTotal + totalConsumos;
        entrada.setValorEntrada(total);
    }

    public void excluir(Long entradaId) {
        Entrada entrada = entradaRepository.findById(entradaId)
                .orElseThrow(() -> new NegocioException("Entrada não encontrado"));
        if (!entradaRepository.existsById(entradaId)) {
            ResponseEntity.notFound().build();
        }
        if (entrada.getQuarto().getStatusQuarto().equals(StatusQuarto.OCUPADO)) {

            var quarto = quartoRepository.findById(entrada.getQuarto().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Não encontrado"));
            quarto.setStatusQuarto(StatusQuarto.LIBERADO);
            quartoRepository.save(quarto);
        }
        entradaRepository.deleteById(entradaId);
    }
}



