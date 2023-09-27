package com.prazeres.services;

import com.prazeres.domain.Consumo;
import com.prazeres.domain.Entrada;
import com.prazeres.repositories.ConsumoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ConsumoService {

    private final ConsumoRepository consumoRepository;

    public ConsumoService(ConsumoRepository consumoRepository) {
        this.consumoRepository = consumoRepository;
    }

    public ResponseEntity<Consumo> buscarPorId(Long consumoId) {
        return consumoRepository.findById(consumoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Void> excluir(Long consumoId) {
        if (!consumoRepository.existsById(consumoId)) {
            return ResponseEntity.notFound().build();
        }
        consumoRepository.deleteById((consumoId));
        return ResponseEntity.noContent().build();
    }
}
