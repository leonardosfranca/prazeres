package com.prazeres.services;

import com.prazeres.domain.Quarto;
import com.prazeres.domain.exceptionhandler.NegocioException;
import com.prazeres.domain.record.QuartoResponse;
import com.prazeres.enums.StatusQuarto;
import com.prazeres.repositories.QuartoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class QuartoService {

    private final QuartoRepository quartoRepository;
    private final ConsumoService consumoService;

    public QuartoService(QuartoRepository quartoRepository, ConsumoService consumoService) {
        this.quartoRepository = quartoRepository;
        this.consumoService = consumoService;
    }

    public List<Quarto> listar() {

        List<Quarto> quartos = quartoRepository.findAll();

        Comparator<Quarto> idComparator = Comparator.comparing(Quarto::getId);
        quartos.sort(idComparator);

        return quartos;
    }

    public List<QuartoResponse> listarPorStatus(StatusQuarto statusQuarto) {
        List<QuartoResponse> quartos = quartoRepository.findAllByStatusQuarto(statusQuarto);
        return quartos;
    }

    public ResponseEntity<Quarto> buscar(Long quartoId) {
        return quartoRepository.findById(quartoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    public Quarto salvar(Quarto quarto) {
        return quartoRepository.save(quarto);
    }

    public ResponseEntity<Quarto> atualizar(Long quartoId,
                                            Quarto requestQuarto) {
        if (!quartoRepository.existsById(quartoId)) {
            return ResponseEntity.notFound().build();
        }
        Quarto quartoExistente = quartoRepository.findById(quartoId)
                .orElseThrow(() -> new NegocioException("Quarto não encontrado"));

        if (quartoExistente.getStatusQuarto() == StatusQuarto.OCUPADO) {
            throw new NegocioException("O quarto está ocupado e não pode ser atualizado");
        }

        requestQuarto.setId(quartoId);
        Quarto quartoAtuazlizado = quartoRepository.save(requestQuarto);

        return ResponseEntity.ok(quartoAtuazlizado);
    }

    public ResponseEntity<Void> remover(Long quartoId) {
        if (!quartoRepository.existsById(quartoId)) {
            return ResponseEntity.notFound().build();
        }
        quartoRepository.deleteById(quartoId);
        return ResponseEntity.noContent().build();
    }

    public void fazerCheckOut(Long quartoId) {
        Quarto quarto = quartoRepository.findById(quartoId)
                .orElseThrow(() -> new NegocioException("Quarto não encontrado"));

        quarto.setStatusQuarto(StatusQuarto.LIBERADO);
        quartoRepository.save(quarto);
    }

}
