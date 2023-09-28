package com.prazeres.services;

import com.prazeres.domain.Entrada;
import com.prazeres.domain.Quarto;
import com.prazeres.domain.exception.EntidadeNaoEncontradaException;
import com.prazeres.domain.exception.NegocioException;
import com.prazeres.enums.StatusEntrada;
import com.prazeres.enums.StatusPagamento;
import com.prazeres.enums.StatusQuarto;
import com.prazeres.enums.TipoPagamento;
import com.prazeres.repositories.EntradaRepository;
import com.prazeres.repositories.QuartoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class EntradaService {

    private final EntradaRepository entradaRepository;
    private final QuartoRepository quartoRepository;

    public EntradaService(EntradaRepository entradaRepository, QuartoRepository quartoRepository) {
        this.entradaRepository = entradaRepository;
        this.quartoRepository = quartoRepository;
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

    public Entrada buscarPorId(Long entradaId) {
        return entradaRepository.findById(entradaId)
                .orElseThrow(() -> new RuntimeException("Entrada não encontrada"));
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
        entrada.setTipoPagamento(TipoPagamento.PENDENTE);
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
        Entrada entrada = entradaRepository.findById(entradaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Entrada não encontrada"));
    }
}
