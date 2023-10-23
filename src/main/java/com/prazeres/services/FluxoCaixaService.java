package com.prazeres.services;

import com.prazeres.domain.FluxoCaixa;
import com.prazeres.domain.exceptionhandler.EntidadeNaoEncontradaException;
import com.prazeres.domain.exceptionhandler.NegocioException;
import com.prazeres.enums.TipoTransacao;
import com.prazeres.repositories.FluxoCaixaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FluxoCaixaService {
    private final FluxoCaixaRepository fluxoCaixaRepository;

    public FluxoCaixaService(FluxoCaixaRepository fluxoCaixaRepository) {
        this.fluxoCaixaRepository = fluxoCaixaRepository;
    }

    public List<FluxoCaixa> listar() {
        return fluxoCaixaRepository.findAll();
    }

    public ResponseEntity<FluxoCaixa> buscarPorId(Long fluxoCaixaId) {
        return fluxoCaixaRepository.findById(fluxoCaixaId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public FluxoCaixa criar(FluxoCaixa fluxoCaixa) {
        if (fluxoCaixa.getValorEntrada() == null) {
            fluxoCaixa.setValorEntrada(0.0);
        }
        Double valorCaixa = fluxoCaixaRepository.valorCaixa();
        if (valorCaixa == null) {
            valorCaixa = 0.0;
        }
        if (fluxoCaixa.getTipo() == TipoTransacao.SAIDA) {
            if (fluxoCaixa.getValorEntrada() > 0.0 && fluxoCaixa.getValorEntrada() > fluxoCaixa.getValorTotal()) {
                throw new NegocioException("A saida não pode ser maior que a entrada");
            }
        }
        return fluxoCaixaRepository.save(fluxoCaixa);
    }
    public Double entradaTotal() {
        List<FluxoCaixa> entradas = fluxoCaixaRepository.findByTipo(TipoTransacao.ENTRADA);
        return entradas.stream()
                .mapToDouble(FluxoCaixa::getValorEntrada)
                .sum();
    }

    public Double saidaTotal() {
        List<FluxoCaixa> saida = fluxoCaixaRepository.findByTipo(TipoTransacao.SAIDA);
        return saida.stream()
                .mapToDouble(FluxoCaixa::getValorEntrada)
                .sum();
    }
    public Double saldoTotal() {
        return entradaTotal() - saidaTotal();
    }

    public FluxoCaixa atualizar(Long fluxoCaixaId, FluxoCaixa fluxoCaixaRequest) {
        FluxoCaixa fluxoCaixa = fluxoCaixaRepository.findById(fluxoCaixaId)
                .orElseThrow(() -> new NegocioException("Fluxo de caixa não encontrado"));
        fluxoCaixa.setValorEntrada(fluxoCaixaRequest.getValorEntrada());
        fluxoCaixa.setValorSaida(fluxoCaixa.getValorSaida());
        fluxoCaixa.setValorTotal(fluxoCaixaRequest.getValorTotal());
        return fluxoCaixaRepository.save(fluxoCaixa);
    }

    public ResponseEntity<Void> excluir(Long fluxoCaixaId) {
        if (!fluxoCaixaRepository.existsById(fluxoCaixaId)) {
            return ResponseEntity.notFound().build();
        }
        fluxoCaixaRepository.deleteById(fluxoCaixaId);
        return ResponseEntity.noContent().build();
    }

    public void exlcuirTudo(FluxoCaixa fluxoCaixa) {
        fluxoCaixaRepository.deleteAll();
    }


}
