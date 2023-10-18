package com.prazeres.services;

import com.prazeres.domain.Consumo;
import com.prazeres.domain.Entrada;
import com.prazeres.domain.FluxoCaixa;
import com.prazeres.domain.Quarto;
import com.prazeres.domain.exceptionhandler.NegocioException;
import com.prazeres.domain.record.ConsumoResumoResponse;
import com.prazeres.domain.record.EntradaListaResponse;
import com.prazeres.domain.record.EntradaResponse;
import com.prazeres.enums.StatusEntrada;
import com.prazeres.enums.StatusPagamento;
import com.prazeres.enums.StatusQuarto;
import com.prazeres.enums.TipoPagamento;
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
                          ConsumoService consumoService, ConsumoRepository consumoRepository, FluxoCaixaRepository fluxoCaixaRepository) {
        this.entradaRepository = entradaRepository;
        this.quartoRepository = quartoRepository;
        this.consumoService = consumoService;
        this.consumoRepository = consumoRepository;
        this.fluxoCaixaRepository = fluxoCaixaRepository;
    }

    public List<EntradaListaResponse> findAll() {
        var listaEntrada = entradaRepository.findAll();
        List<EntradaListaResponse> entradaListaResponseList = new ArrayList<>();
        listaEntrada.forEach(listaEntrada1 -> {
            EntradaListaResponse entradaListaResponse = new EntradaListaResponse(
                    listaEntrada1.getId(),
                    listaEntrada1.getPlacaVeiculo(),
                    listaEntrada1.getQuarto(),
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

    public EntradaResponse buscarPorId(Long entradaId) {
        var entrada = entradaRepository.findById(entradaId)
                .orElseThrow(() -> new NegocioException("Consumo não encontrada"));
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
        return new EntradaResponse(
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
        entrada.setStatusPagamento(StatusPagamento.PENDENTE);
        quarto.setStatusQuarto(StatusQuarto.OCUPADO);
        entrada.setTipoPagamento(TipoPagamento.A_PAGAR);
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


            FluxoCaixa fluxoCaixa = new FluxoCaixa();
            fluxoCaixa.setRegistroVenda(LocalDateTime.now());
            var relatorio = validacaoRelatorio(fluxoCaixa.getDescricao());

            var valorTotal = fluxoCaixaRepository.valorCaixa() + entrada.getValorEntrada();
            fluxoCaixa.setDescricao(relatorio);
            fluxoCaixa.setQuarto(entrada.getQuarto().getNumero());
            fluxoCaixa.setValorEntrada(entrada.getValorEntrada());
            fluxoCaixa.setValorSaida(0D);
            fluxoCaixa.setValorTotal(valorTotal);

            fluxoCaixaRepository.save(fluxoCaixa);
            entradaRepository.save(entrada);
        }
        return entrada;

    }

    private String validacaoRelatorio(String relatorio) {
        LocalTime noite = LocalTime.of(18, 0);
        LocalTime dia = LocalTime.of(6, 0);

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
        double custoAdicional = 0D;

        if (minutosTotais > 120) {
            custoAdicional = Math.ceil(minutosTotais / 30.0) * 5.0;
        }

        List<Consumo> consumos = entrada.getConsumos();
        double totalConsumos = consumos.stream().mapToDouble(Consumo::getSubTotal).sum();
        double valorTotal = entrada.getValorEntrada() + custoAdicional + totalConsumos;
        entrada.setValorEntrada(valorTotal);

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



