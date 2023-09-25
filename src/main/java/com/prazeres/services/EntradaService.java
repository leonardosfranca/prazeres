package com.prazeres.services;

import com.prazeres.controllers.EntradaController;
import com.prazeres.domain.Entrada;
import com.prazeres.repositories.EntradaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EntradaService {

    private EntradaRepository entradaRepository;

    public EntradaService(EntradaRepository entradaRepository) {
        this.entradaRepository = entradaRepository;
    }

    public Entrada findById(Long id) {
        return entradaRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(""));
    }
}
