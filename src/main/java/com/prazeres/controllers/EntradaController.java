package com.prazeres.controllers;

import com.prazeres.domain.Entrada;
import com.prazeres.domain.Quarto;
import com.prazeres.domain.exceptionhandler.NegocioException;
import com.prazeres.domain.record.EntradaListaResponse;
import com.prazeres.domain.record.EntradaResponse;
import com.prazeres.enums.StatusEntrada;
import com.prazeres.repositories.EntradaRepository;
import com.prazeres.services.EntradaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/entradas")
public class EntradaController {
    private final EntradaService entradaService;
    private final EntradaRepository entradaRepository;

    public EntradaController(EntradaService entradaService, EntradaRepository entradaRepository) {
        this.entradaService = entradaService;
        this.entradaRepository = entradaRepository;
    }

    @GetMapping("/listarPorEntrada")
    public List<EntradaListaResponse> listarPorEntrada() {
        return entradaService.findAll();
    }

    @GetMapping("/{buscarPorId}")
    public ResponseEntity<Entrada> buscarPorId(@PathVariable Long buscarPorId) {
        return entradaRepository.findById(buscarPorId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/statusEntrada")
    public List<Entrada> listarPorStatus(StatusEntrada statusEntrada) {
        return entradaService.listarPorStatus(statusEntrada);
    }
    @GetMapping("/dataAtual")
    public List<Entrada> listarDataAtual() {
        return entradaService.listarPorDataAtual();
    }

    @GetMapping("/dataRegistro")
    public List<Entrada> listarPorDataRegistro(LocalDate dataRegistro) {
        return entradaService.listarPorDataRegistro(dataRegistro);
    }

    @GetMapping("/buscarTodosOsConsumosPorIdEntrada/{entradaId}")
    public EntradaResponse buscarTodosOsConsumosPorIdEntrada(@PathVariable Long entradaId) {
        return entradaService.buscar(entradaId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Entrada salvar(Entrada entrada) {
        return entradaService.salvaEntrada(entrada);
    }

    @PutMapping("/{entradaId}")
    public Entrada atualizar(@PathVariable Long entradaId,
                             Entrada entrada) {
        return entradaService.atualizar(entradaId, entrada);
    }

    @DeleteMapping("/{entradaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long entradaId) {
        entradaService.excluir(entradaId);
    }


    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<String> capturar(NegocioException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
