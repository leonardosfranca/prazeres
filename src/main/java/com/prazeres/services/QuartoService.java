package com.prazeres.services;

import com.prazeres.domain.Quarto;
import com.prazeres.domain.exceptionhandler.NegocioException;
import com.prazeres.enums.StatusQuarto;
import com.prazeres.repositories.QuartoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    public Quarto buscarPorId(Long quartoId) {
        return quartoRepository.findById(quartoId)
                .orElseThrow(() -> new NegocioException("Quarto não encontrado"));
    }

    public Quarto salvar(Quarto quarto) {
        return quartoRepository.save(quarto);
    }
    public Quarto atualizar(Long quartoId, Quarto requestQuarto) {
        Quarto quarto = quartoRepository.findById(quartoId)
                .orElseThrow(()-> new NegocioException("Quarto não encontrado"));
        quarto.setStatusQuarto(requestQuarto.getStatusQuarto());
        quarto.setTipoQuarto(requestQuarto.getTipoQuarto());
        quarto.setNumero(requestQuarto.getNumero());
        quarto.setDescricao(requestQuarto.getDescricao());
        quarto.setCapacidadePessoas(requestQuarto.getCapacidadePessoas());
        return quartoRepository.save(quarto);
    }

    public void excluir(Long quartoId) {
        Quarto quarto = quartoRepository.findById(quartoId)
                .orElseThrow(()-> new NegocioException("Quarto não encontrado"));
        if (!quartoRepository.existsById(quartoId)) {
            ResponseEntity.notFound().build();
        }
        quartoRepository.deleteById(quartoId);
    }

    public List<Quarto> listarPorStatus(StatusQuarto statusQuarto) {
        return quartoRepository.findAllByStatusQuarto(statusQuarto);
    }

    public void fazerCheckOut(Long quartoId) {
        Quarto quarto = quartoRepository.findById(quartoId)
                .orElseThrow(() -> new NegocioException("Quarto não encontrado"));

        quarto.setStatusQuarto(StatusQuarto.LIBERADO);
        quartoRepository.save(quarto);
    }

}
