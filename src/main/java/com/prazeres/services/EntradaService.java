package com.prazeres.services;

import com.prazeres.domain.Consumo;
import com.prazeres.domain.Entrada;
import com.prazeres.domain.Quarto;
import com.prazeres.domain.exception.EntidadeNaoEncontradaException;
import com.prazeres.domain.exception.NegocioException;
import com.prazeres.domain.record.ConsumoResumoResponse;
import com.prazeres.domain.record.EntradaListaResponse;
import com.prazeres.domain.record.EntradaResponse;
import com.prazeres.enums.StatusEntrada;
import com.prazeres.enums.StatusPagamento;
import com.prazeres.enums.StatusQuarto;
import com.prazeres.enums.TipoPagamento;
import com.prazeres.repositories.ConsumoRepository;
import com.prazeres.repositories.EntradaRepository;
import com.prazeres.repositories.QuartoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EntradaService {

    private final EntradaRepository entradaRepository;
    private final QuartoRepository quartoRepository;
    private final ConsumoService consumoService;
    private final ConsumoRepository consumoRepository;

    public EntradaService(EntradaRepository entradaRepository, QuartoRepository quartoRepository,
                          ConsumoService consumoService, ConsumoRepository consumoRepository) {
        this.entradaRepository = entradaRepository;
        this.quartoRepository = quartoRepository;
        this.consumoService = consumoService;
        this.consumoRepository = consumoRepository;
    }

    public List<EntradaListaResponse> findAll() {
        var listaEntrada = entradaRepository.findAll();
        List<EntradaListaResponse> entradaListaResponseList = new ArrayList<>();
        listaEntrada.forEach(listaEntrada1 -> {
            EntradaListaResponse entradaListaResponse = new EntradaListaResponse(
                    listaEntrada1.getId(),
                    listaEntrada1.getPlacaVeiculo(),
                    new EntradaListaResponse.Quarto(listaEntrada1.getQuarto().getNumero()),
                    listaEntrada1.getHorarioEntrada(),
                    listaEntrada1.getHorarioSaida(),
                    listaEntrada1.getStatusEntrada(),
                    listaEntrada1.getDataRegistro(),
                    listaEntrada1.getStatusPagamento());
            entradaListaResponseList.add(entradaListaResponse);
        });
        return entradaListaResponseList;
    }


    public List<Entrada> listarPorStatus(StatusEntrada statusEntrada) {
        return entradaRepository.findAllByStatusEntrada(statusEntrada);
    }

    public List<Entrada> listarDataAtual() {
        LocalDate hoje = LocalDate.now();
        return entradaRepository.findAllByDataRegistro(hoje);
    }

    public List<Entrada> listarPorDataRegistro(LocalDate dataRegistro) {
        return entradaRepository.findAllByDataRegistro(dataRegistro);
    }

    public EntradaResponse buscarPorId(Long entradaId) {
        var entrada = entradaRepository.findById(entradaId)
                .orElseThrow(() -> new RuntimeException("Entrada não encontrada"));
        var listaConsumo = consumoService.findConsumoByEntrdaId(entradaId);

        List<ConsumoResumoResponse> consumoResumoResponseList = new ArrayList<>();

        listaConsumo.forEach(consumno -> {
            ConsumoResumoResponse consumoResumoResponse = new ConsumoResumoResponse(
                    consumno.quantidade(),
                    consumno.descricao(),
                    consumno.valor()
            );
            consumoResumoResponseList.add(consumoResumoResponse);
        });

        var totalConsumo = consumoRepository.valorConsumo();
        var valorTotal = totalConsumo + entrada.getValorEntrada();
        EntradaResponse entradaResponse = new EntradaResponse(
                entrada.getPlacaVeiculo(),
                new EntradaResponse.Quarto(entrada.getQuarto().getCapacidadePessoas()),
                consumoResumoResponseList,
                entrada.getHorarioEntrada(),
                entrada.getStatusEntrada(),
                entrada.getDataRegistro(),
                entrada.getTipoPagamento(),
                entrada.getStatusPagamento(),
                totalConsumo,
                entrada.getValorEntrada(),
                valorTotal


        );
        return entradaResponse;
    }

    public Entrada salvar(Entrada entrada) {
        Quarto quarto = quartoRepository.findById(entrada.getQuarto().getId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Quarto não encontrado"));

        if (quarto.getStatusQuarto().equals(StatusQuarto.OCUPADO)) {
            throw new NegocioException("Quarto ocupado");
        }
        salvar(entrada, quarto);
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
            case SUITE_ECONOMICA -> entrada.setValorEntrada(30D);
            case SUITE_EXECUTIVA -> entrada.setValorEntrada(60D);
        }
    }

    private void salvar(Entrada entrada, Quarto quarto) {
        entrada.setHorarioEntrada(LocalTime.now());
        entrada.setDataRegistro(LocalDate.now());
        entrada.setStatusEntrada(StatusEntrada.EM_ANDAMENTO);
        entrada.setStatusPagamento(StatusPagamento.PENDENTE);
        quarto.setStatusQuarto(StatusQuarto.OCUPADO);
        entrada.setTipoPagamento(TipoPagamento.A_PAGAR);
    }

    public Entrada atualizar(Long entradaId, Entrada entradaRequest) {
        Entrada entrada = entradaRepository.findById(entradaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Entrada não encontrada"));
        entrada.setPlacaVeiculo(entradaRequest.getPlacaVeiculo());
        entrada.setStatusEntrada(entradaRequest.getStatusEntrada());
        entrada.setTipoPagamento(entradaRequest.getTipoPagamento());
        entrada.setStatusPagamento(entradaRequest.getStatusPagamento());

        if (entrada.getStatusEntrada().equals(StatusEntrada.FECHADA)) {
            LocalTime horarioEntrada = entrada.getHorarioEntrada();
            LocalTime horarioSaida = LocalTime.now();

            Duration tempoPermanecido = Duration.between(horarioEntrada, horarioSaida);
            long minutosTotais = tempoPermanecido.toMinutes();
            double custoAdicional = 0;

            if (minutosTotais > 120) {
                custoAdicional = Math.ceil(minutosTotais / 30.0) * 5.0;
            }

            List<Consumo> consumos = entrada.getConsumos();
            double totalConsumos = consumos.stream().mapToDouble(Consumo::getSubTotal).sum();
            double valorTotal = entrada.getValorEntrada() + custoAdicional + totalConsumos;
            entrada.setValorEntrada(valorTotal);
            entrada.setHorarioSaida(horarioSaida);
        }
        entradaRepository.save(entrada);
        return entrada;
    }

    public void excluir(Long entradaId) {
        Entrada entrada = entradaRepository.findById(entradaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Entrada não encontrado"));
        if (!entradaRepository.existsById(entradaId)) {
            ResponseEntity.notFound().build();
        }
        entradaRepository.deleteById(entradaId);
    }

    /*        Entrada entrada = entradaRepository.findById(entradaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Entrada não encontrada"));
        Entrada novaEntrada = new Entrada(
                entrada.getId(),
                entradaRequest.getPlacaVeiculo(),
                entrada.getQuarto(),
                entrada.getConsumos(),
                entrada.getHorarioEntrada(),
                entrada.getHorarioSaida(),
                entradaRequest.getStatusEntrada(),
                entrada.getDataRegistro(),
                entradaRequest.getTipoPagamento(),
                entradaRequest.getStatusPagamento(),
                entrada.getValorEntrada()
        );
        entradaRepository.save(novaEntrada);

        if (entrada.getStatusEntrada().equals(StatusEntrada.FECHADA)) {
            LocalTime horarioEntrada = entrada.getHorarioEntrada();
            LocalTime horarioSaida = LocalTime.now();

            Duration tempoPermanecido = Duration.between(horarioEntrada, horarioSaida);
            long minutosTotais = tempoPermanecido.toMinutes();
            double custoAdicional = 0;

            if (minutosTotais > 120) {
                custoAdicional = Math.ceil(minutosTotais / 30.0) * 5.0;
            }

            List<Consumo> consumos = entrada.getConsumos();
            double totalConsumos = consumos.stream().mapToDouble(Consumo::getSubTotal).sum();
            double valorTotal = entrada.getValorEntrada() + custoAdicional + totalConsumos;
            entrada.setValorEntrada(valorTotal);
            entrada.setHorarioSaida(horarioSaida);
            entradaRepository.save(entrada);
        }
        return entrada;*/
}



