package com.prazeres.services;

import com.prazeres.domain.FluxoCaixa;
import com.prazeres.domain.exceptionhandler.EntidadeNaoEncontradaException;
import com.prazeres.domain.exceptionhandler.NegocioException;
import com.prazeres.enums.TipoMovimentacao;
import com.prazeres.repositories.EntradaRepository;
import com.prazeres.repositories.FluxoCaixaRepository;
import com.prazeres.repositories.QuartoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FluxoCaixaService {
    private final FluxoCaixaRepository fluxoCaixaRepository;
    private final EntradaRepository entradaRepository;
    private List<FluxoCaixa> entradas = new ArrayList<>();

    public FluxoCaixaService(FluxoCaixaRepository fluxoCaixaRepository, QuartoRepository quartoRepository, EntradaRepository entradaRepository) {
        this.fluxoCaixaRepository = fluxoCaixaRepository;
        this.entradaRepository = entradaRepository;
    }

    public List<FluxoCaixa> listarTodasMovimentacoes() {
        return fluxoCaixaRepository.findAll();
    }

    public FluxoCaixa buscarMovimentacoesPorId(Long fluxoCaixaId) {
        return fluxoCaixaRepository.findById(fluxoCaixaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Id não encontrado"));
    }

    public List<FluxoCaixa> listarMovimentacoesEntrada() {
        return fluxoCaixaRepository.findByTipo(TipoMovimentacao.ENTRADA);
    }

    public List<FluxoCaixa> listarMovimentacoesSaida() {
        return fluxoCaixaRepository.findByTipo(TipoMovimentacao.SAIDA);
    }

    public FluxoCaixa criarMovimentacao(FluxoCaixa fluxo) {
        fluxo.setRegistroVenda(LocalDate.now());
        if (fluxo.getValor() == null) {
            fluxo.setValor(0D);
        }
        double valorTotal = calcularTotal();
        if (fluxo.getTipo() == TipoMovimentacao.SAIDA && fluxo.getValor() > valorTotal) {
            throw new NegocioException("O valor de saída é maior que o valor total disponível.");
        }
        return fluxoCaixaRepository.save(fluxo);
    }

    public double calcularTotal() {
        List<FluxoCaixa> fluxoCaixaEntrada = fluxoCaixaRepository.findByTipo(TipoMovimentacao.ENTRADA);
        List<FluxoCaixa> fluxoCaixaSaida = fluxoCaixaRepository.findByTipo(TipoMovimentacao.SAIDA);

        double totalEntrada = fluxoCaixaEntrada
                .stream()
                .mapToDouble(FluxoCaixa::getValor)
                .sum();
        double totalSaida = fluxoCaixaSaida
                .stream()
                .mapToDouble(FluxoCaixa::getValor)
                .sum();

        return totalEntrada - totalSaida;
    }


}
