package com.prazeres.services;

import com.prazeres.domain.Quarto;
import com.prazeres.domain.exception.EntidadeNaoEncontradaException;
import com.prazeres.domain.record.ConsumoResumoResponse;
import com.prazeres.domain.record.QuartoResponse;
import com.prazeres.enums.StatusQuarto;
import com.prazeres.repositories.QuartoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        return quartoRepository.findAll();
    }


    public Quarto salvar(Quarto quarto) {
        return quartoRepository.save(quarto);
    }
//    public Quarto atualizar(Long quartoId, Quarto request) {
//        Quarto antigoQuarto =  quartoRepository.findById(quartoId)
//                .orElseThrow(()-> new EntidadeNaoEncontradaException("Quarto não encontrado"));
//        Quarto novoQuarto = new Quarto(
//                antigoQuarto.getId(),
//                antigoQuarto.getDescricao(),
//                antigoQuarto.getCapacidadePessoas(),
//                request.getStatusQuarto(),
//                request.getTipoQuarto()
//        );
//        return quartoRepository.save(novoQuarto);
//    }

    public ResponseEntity<Quarto> atualizar(Long quartoId, Quarto quarto) {
        if (!quartoRepository.existsById(quartoId)) {
            return ResponseEntity.notFound().build();
        }
        quarto.setId(quartoId);
        Quarto quartoAtualizado = quartoRepository.save(quarto);
        return ResponseEntity.ok(quartoAtualizado);
    }

    public void excluir(Long quartoId) {
        quartoRepository.deleteById(quartoId);
    }

    public List<Quarto> listarPorStatus(StatusQuarto statusQuarto) {
        return quartoRepository.findAllByStatusQuarto(statusQuarto);
    }


}
