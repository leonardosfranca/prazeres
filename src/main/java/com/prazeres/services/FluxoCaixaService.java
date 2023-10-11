package com.prazeres.services;

import com.prazeres.domain.FluxoCaixa;
import com.prazeres.domain.exceptionhandler.EntidadeException;
import com.prazeres.domain.exceptionhandler.NegocioException;
import com.prazeres.repositories.FluxoCaixaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    public FluxoCaixa buscarPorId(Long fluxoCaixaId) {
        return fluxoCaixaRepository.findById(fluxoCaixaId)
                .orElseThrow(()-> new EntidadeException("Não encontrado"));
    }

    public FluxoCaixa criar(FluxoCaixa fluxoCaixa) {
        return fluxoCaixaRepository.save(fluxoCaixa);
    }

    public FluxoCaixa atualizar(Long fluxoCaixaId, FluxoCaixa fluxoCaixaRequest) {
        FluxoCaixa fluxoCaixa = fluxoCaixaRepository.findById(fluxoCaixaId)
                .orElseThrow(()-> new NegocioException("Fluxo de caixa não encontrado"));
        fluxoCaixa.setValorEntrada(fluxoCaixaRequest.getValorEntrada());
        fluxoCaixa.setValorSaida(fluxoCaixa.getValorSaida());
        fluxoCaixa.setValorTotal(fluxoCaixaRequest.getValorTotal());
        return fluxoCaixaRepository.save(fluxoCaixa);
    }

    public void excluir(Long fluxoCaixaId) {
        FluxoCaixa fluxoCaixa = fluxoCaixaRepository.findById(fluxoCaixaId)
                .orElseThrow(()-> new NegocioException("Fluxo não encontrado"));
        fluxoCaixaRepository.deleteById(fluxoCaixa.getId());
    }
}
