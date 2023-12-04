package com.prazeres.services;

import com.prazeres.domain.Consumo;
import com.prazeres.domain.Entrada;
import com.prazeres.domain.FluxoCaixa;
import com.prazeres.domain.Quarto;
import com.prazeres.domain.exceptionhandler.EntidadeNaoEncontradaException;
import com.prazeres.domain.exceptionhandler.NegocioException;
import com.prazeres.domain.record.*;
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
import java.time.LocalTime;
import java.time.Period;
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
                    new EntradaResumoResponse.Quarto(
                            entradas.getQuarto().getStatusQuarto(),
                            entradas.getQuarto().getTipoQuarto()),
                    entradas.getHorarioEntrada(),
                    entradas.getHorarioSaida(),
                    entradas.getStatusEntrada(),
                    entradas.getStatusPagamento());
            entradaResumoResponseList.add(entradaResumoResponse);
        });
        return entradaResumoResponseList;
    }

    public EntradaBuscaIdResponse buscaEntradaId(Long entradaId) {
        Entrada entrada = entradaRepository.findById(entradaId)
                .orElseThrow(()-> new EntidadeNaoEncontradaException("Entrada não encontrada"));

        var listaConsumo = consumoRepository.findAllByEntrada_Id(entradaId);

        List<ConsumoResponse> consumo = new ArrayList<>();
        listaConsumo.forEach(listaConsumo1 -> {
            ConsumoResponse consumoListaResponse = new ConsumoResponse(
                    listaConsumo1.getId(),
                    listaConsumo1.getQuantidade(),
                    listaConsumo1.getItem().getDescricao(),
                    listaConsumo1.getItem().getValor(),
                    consumoService.subTotal(listaConsumo1));
            consumo.add(consumoListaResponse);
        });

        double valorConsumo = listaConsumo.stream()
                .mapToDouble(consumoItem -> consumoItem.getValor())
                .sum();

        var valorTotal = valorConsumo + entrada.getValorEntrada();
        return new EntradaBuscaIdResponse(
                new EntradaBuscaIdResponse.Quarto(entrada.getQuarto().getNumero()),
                entrada.getHorarioEntrada(),
                entrada.getHorarioSaida(),
                entrada.getPlacaVeiculo(),
                consumo,
                entrada.getStatusEntrada(),
                valorConsumo,
                entrada.getValorEntrada(),
                valorTotal
        );
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

    public EntradaBuscaResponse buscarTodosOsConsumosPorIdEntrada(Long entradaId) {
        Entrada entrada = entradaRepository.findById(entradaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Entrada não encontrada"));
        List<Consumo> consumos = consumoRepository.findAllByEntrada_Id(entradaId);
        if (consumos.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Entrada sem consumo");
        }

        List<ConsumoResponse> consumoResponseList = new ArrayList<>();

        consumos.forEach(consumo -> {
            consumo.setValorTotal(consumo.getQuantidade() * consumo.getItem().getValor());
            ConsumoResponse consumoResponse = new ConsumoResponse(
                    consumo.getId(),
                    consumo.getQuantidade(),
                    consumo.getItem().getDescricao(),
                    consumo.getItem().getValor(),
                    consumoService.subTotal(consumo)
            );
            consumoResponseList.add(consumoResponse);
        });
        return valorConsumo(entradaId, entrada, consumoResponseList);

    }
    private EntradaBuscaResponse valorConsumo(Long entradaId, Entrada entrada, List<ConsumoResponse> consumoResponseList) {
        var valorConsumo = consumoRepository.valorConsumo(entradaId);
        var valorTotal = valorConsumo + entrada.getValorEntrada();
        return new EntradaBuscaResponse(
                new EntradaBuscaResponse.Quarto(entrada.getQuarto().getNumero()),
                entrada.getDataRegistro(),
                entrada.getHorarioEntrada(),
                entrada.getStatusEntrada(),
                entrada.getTipoPagamento(),
                entrada.getStatusPagamento(),
                consumoResponseList,
                entrada.getValorEntrada(),
                valorConsumo,
                valorTotal
        );
    }


    public Entrada salvaEntrada(Entrada entrada) {
        Quarto quarto = quartoRepository.findById(entrada.getQuarto().getId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Quarto não encontrado"));

        if (quarto.getStatusQuarto().equals(StatusQuarto.OCUPADO)) {
            throw new NegocioException("Quarto ocupado");
        }
        entrada.setStatusEntrada(StatusEntrada.EM_ANDAMENTO);
        entrada.setValorEntrada(quarto.getValor());
        salvaEntrada(entrada, quarto);


        return entrada;
    }

    private void salvaEntrada(Entrada entrada, Quarto quarto) {
        entrada.setHorarioEntrada(LocalTime.now());
        entrada.setDataRegistro(LocalDate.now());
        entrada.setStatusEntrada(StatusEntrada.EM_ANDAMENTO);
        entrada.setStatusPagamento(StatusPagamento.PENDENTE);
        quarto.setStatusQuarto(StatusQuarto.OCUPADO);
        entrada.setTipoPagamento(TipoPagamento.PENDENTE);

        entradaRepository.save(entrada);
        quartoRepository.save(quarto);
    }

    public Entrada atualizar(Long entradaId, Entrada entradaRequest) {
        Entrada entrada = entradaRepository.findById(entradaId)
                .orElseThrow(() -> new NegocioException("Entrada não encontrada"));

        entrada.setStatusEntrada(entradaRequest.getStatusEntrada());
        entrada.setTipoPagamento(entradaRequest.getTipoPagamento());
        entrada.setStatusPagamento(entradaRequest.getStatusPagamento());

        StatusPagamento novoStatusPagamento = entradaRequest.getStatusPagamento();

        if (novoStatusPagamento != null) {
            if (novoStatusPagamento == StatusPagamento.PAGO) {
                novoStatusPagamento = StatusPagamento.FINALIZADO;
            }
        }
        entrada.setStatusPagamento(novoStatusPagamento);

        if (!entradaRequest.getTipoPagamento().equals(TipoPagamento.PENDENTE)) {
            validacaoHorario(entrada);

            if (novoStatusPagamento == StatusPagamento.FINALIZADO) {
                entrada.setStatusEntrada(StatusEntrada.FINALIZADA);
                salvaNoCaixa(entrada);
            }
            entradaRepository.save(entrada);
        }
        return entrada;
    }
    private void salvaNoCaixa(Entrada entrada) {
        FluxoCaixa fluxoCaixa = new FluxoCaixa();
        fluxoCaixa.setRegistroVenda(LocalDate.now());
        var relatorio = validacaoRelatorio();

        double valorTotal = (fluxoCaixaRepository.valorCaixa() != null) ? entrada.getValorEntrada() : 0;

        fluxoCaixa.setDescricao(relatorio);
        fluxoCaixa.setTipo(TipoMovimentacao.ENTRADA);
        fluxoCaixa.setTipo(TipoMovimentacao.SAIDA);
        fluxoCaixa.setValor(valorTotal);

        fluxoCaixaRepository.save(fluxoCaixa);

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

        double totalConsumos = (consumoRepository.valorConsumo(entrada.getId()) != null) ? consumoRepository.valorConsumo(entrada.getId()) : 0;

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



