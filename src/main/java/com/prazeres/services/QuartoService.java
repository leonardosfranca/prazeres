package com.prazeres.services;

import com.prazeres.domain.Quarto;
import com.prazeres.domain.exception.EntidadeNaoEncontradaException;
import com.prazeres.repositories.QuartoRepository;
import org.springframework.stereotype.Service;

@Service
public class QuartoService {

    private final QuartoRepository quartoRepository;

    public QuartoService(QuartoRepository quartoRepository) {
        this.quartoRepository = quartoRepository;
    }

    public Quarto buscar(Long quartoId) {
        return quartoRepository.findById(quartoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Quarto não encontrado!"));
    }
}
