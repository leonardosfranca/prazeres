package com.prazeres.services;

import com.prazeres.domain.Quarto;
import com.prazeres.domain.exceptionhandler.NegocioException;
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

        // Crie um Comparator personalizado para comparar os quartos pelo ID em ordem crescente
        Comparator<Quarto> idComparator = Comparator.comparing(Quarto::getId);

        // Ordene a lista de quartos usando o Comparator personalizado
        quartos.sort(idComparator);

        return quartos;
    }

    public List<Quarto> listarPorStatus(StatusQuarto statusQuarto) {
        //return quartoRepository.findAllByStatusQuarto(statusQuarto);
        List<Quarto> quartos = quartoRepository.findAllByStatusQuarto(statusQuarto);

        // Crie um Comparator personalizado para comparar os quartos pelo ID em ordem crescente
        Comparator<Quarto> idComparator = Comparator.comparing(Quarto::getId);

        // Ordene a lista de quartos usando o Comparator personalizado
        quartos.sort(idComparator);

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
