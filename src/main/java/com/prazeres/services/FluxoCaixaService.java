package com.prazeres.services;

import com.prazeres.domain.FluxoCaixa;
import com.prazeres.domain.exceptionhandler.EntidadeNaoEncontradaException;
import com.prazeres.domain.exceptionhandler.NegocioException;
import com.prazeres.repositories.EntradaRepository;
import com.prazeres.repositories.FluxoCaixaRepository;
import com.prazeres.repositories.QuartoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public List<FluxoCaixa> listar() {
        return fluxoCaixaRepository.findAll();
    }

    public FluxoCaixa buscarPorId(Long fluxoCaixaId) {
        return fluxoCaixaRepository.findById(fluxoCaixaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Id não encontrado"));
    }

    public FluxoCaixa salvarFluxoCaixa(FluxoCaixa fluxo) {
        FluxoCaixa fluxoCaixa = new FluxoCaixa();

        if (fluxo.getValorSaida() > fluxo.getValorTotal()) {
            throw new NegocioException("Valor de saída não permitido, verifique o saldo");
        }

        if (fluxo.getValorEntrada() == null) {
            fluxo.setValorEntrada(0.0);
        }

        if (fluxo.getValorSaida() == null) {
            fluxo.setValorSaida(0.0);
        }

        fluxo.setValorTotal(fluxo.getValorEntrada() - fluxo.getValorSaida());
        fluxoCaixa.setRegistroVenda(LocalDateTime.now());

        var fluxoRepositorio = fluxoCaixaRepository.save(fluxo);
        return fluxoRepositorio;
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
