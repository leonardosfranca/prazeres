package com.prazeres.services;

import com.prazeres.domain.Entrada;
import com.prazeres.domain.Quarto;
import com.prazeres.domain.exception.EntidadeNaoEncontradaException;
import com.prazeres.domain.exception.NegocioException;
import com.prazeres.domain.record.ConsumoResumoResponse;
import com.prazeres.domain.record.EntradaResponse;
import com.prazeres.enums.StatusEntrada;
import com.prazeres.enums.StatusPagamento;
import com.prazeres.enums.StatusQuarto;
import com.prazeres.enums.TipoPagamento;
import com.prazeres.repositories.EntradaRepository;
import com.prazeres.repositories.QuartoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class EntradaService {

    private final EntradaRepository entradaRepository;
    private final QuartoRepository quartoRepository;
    private final ConsumoService consumoService;

    public EntradaService(EntradaRepository entradaRepository, QuartoRepository quartoRepository, ConsumoService consumoService) {
        this.entradaRepository = entradaRepository;
        this.quartoRepository = quartoRepository;
        this.consumoService = consumoService;
    }

    public List<Entrada> listar() {
        return entradaRepository.findAll();
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

    public AtomicReference<EntradaResponse> buscarPorId(Long entradaId) {
        var entrada = entradaRepository.findById(entradaId)
                .orElseThrow(() -> new RuntimeException("Entrada não encontrada"));
        var listaConsumo = consumoService.findConsumoByEntrdaId(entradaId);
        AtomicReference<EntradaResponse> entradaResponse = new AtomicReference<>();
        List<ConsumoResumoResponse> consumoResumoResponseList = new ArrayList<>();
        listaConsumo.forEach(b -> {
            ConsumoResumoResponse consumoResumoResponse = new ConsumoResumoResponse(
                    b.quantidade(),
                    b.descricao()
            );
            consumoResumoResponseList.add(consumoResumoResponse);
            if (consumoResumoResponse == null) {
                consumoResumoResponse = new ConsumoResumoResponse(
                        0,
                        "Sem consumo");
            }
        });
        listaConsumo.forEach(a -> {
            entradaResponse.set(new EntradaResponse(
                    entrada.getPlacaVeiculo(),
                    new EntradaResponse.Quarto(entrada.getQuarto().getCapacidadePessoas()),
                    consumoResumoResponseList,
                    entrada.getHorarioEntrada(),
                    entrada.getStatusEntrada(),
                    entrada.getDataRegistro(),
                    entrada.getTipoPagamento(),
                    entrada.getStatusPagamento()
            ));
        });
        return entradaResponse;
    }

    public Entrada salvar(Entrada entrada) {
        Quarto quarto = quartoRepository.findById(entrada.getQuarto().getId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Quarto não encontrado"));

        if (quarto.getStatusQuarto().equals(StatusQuarto.OCUPADO)) {
            throw new NegocioException("Quarto ocupado");
        }

        entrada.setHorarioEntrada(LocalTime.now());
        entrada.setDataRegistro(LocalDate.now());
        entrada.setStatusEntrada(StatusEntrada.EM_ANDAMENTO);
        entrada.setStatusPagamento(StatusPagamento.PENDENTE);
        quarto.setStatusQuarto(StatusQuarto.OCUPADO);
        entrada.setTipoPagamento(TipoPagamento.A_PAGAR);
        quartoRepository.save(quarto);

        return entradaRepository.save(entrada);
    }

    public Entrada atualizar(Long entradaId, Entrada request) {
        Entrada entrada = entradaRepository.findById(entradaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Entrada não encontrada"));
        Entrada novaEntrada = new Entrada(
                entrada.getId(),
                request.getPlacaVeiculo(),
                entrada.getQuarto(),
                entrada.getConsumos(),
                entrada.getHorarioEntrada(),
                entrada.getStatusEntrada(),
                entrada.getDataRegistro(),
                request.getTipoPagamento(),
                request.getStatusPagamento(),
                entrada.getValorTotal()
        );
        return entradaRepository.save(novaEntrada);

    }

    public void excluir(Long entradaId) {
        entradaRepository.deleteById(entradaId);
    }
}
