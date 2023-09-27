package com.prazeres.services;

import com.prazeres.domain.Entrada;
import com.prazeres.repositories.EntradaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntradaService {

    private final EntradaRepository entradaRepository;

    public EntradaService(EntradaRepository entradaRepository) {
        this.entradaRepository = entradaRepository;
    }

    public List<Entrada> listar() {
        return entradaRepository.findAll();
    }
    public ResponseEntity<Entrada> buscarPorId(Long entradaId) {
        return entradaRepository.findById(entradaId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public Entrada salvar(Entrada entrada) {
        return entradaRepository.save(entrada);
    }
    public ResponseEntity<Entrada> atualizar(Long entradaId, Entrada entrada) {
        if (!entradaRepository.existsById(entradaId)) {
            return ResponseEntity.notFound().build();
        }
        entrada.setId(entradaId);
        Entrada entradaAtualizada = entradaRepository.save(entrada);
        return ResponseEntity.ok(entradaAtualizada);
    }

    public ResponseEntity<Void> excluir(Long entradaId) {
        if (!entradaRepository.existsById(entradaId)) {
            return ResponseEntity.notFound().build();
        }
        entradaRepository.deleteById(entradaId);
        return ResponseEntity.noContent().build();
    }
}
