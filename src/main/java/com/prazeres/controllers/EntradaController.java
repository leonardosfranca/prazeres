package com.prazeres.controllers;

import com.prazeres.domain.Entrada;
import com.prazeres.domain.exception.EntidadeNaoEncontradaException;
import com.prazeres.domain.record.EntradaResponse;
import com.prazeres.enums.StatusEntrada;
import com.prazeres.services.EntradaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/entradas")
public class EntradaController {
    private final EntradaService entradaService;

    public EntradaController(EntradaService entradaService) {
        this.entradaService = entradaService;
    }

    @GetMapping
    public List<Entrada> listar() {
        return entradaService.listar();
    }

    @GetMapping("/status")
    public List<Entrada> listarPorStatus(StatusEntrada statusEntrada) {
        return entradaService.listarPorStatus(statusEntrada);
    }
    @GetMapping("/dataAtual")
    public List<Entrada> listarDataAtual() {
        return entradaService.listarDataAtual();
    }

    @GetMapping("/dataRegistro")
    public List<Entrada> listarPorDataRegistro(LocalDate dataRegistro) {
        return entradaService.listarPorDataRegistro(dataRegistro);
    }

    @GetMapping("/{entradaId}")
    public AtomicReference<EntradaResponse> buscarPorId(@PathVariable Long entradaId) {
        return entradaService.buscarPorId(entradaId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Entrada salvar(Entrada entrada) {
        return entradaService.salvar(entrada);
    }

    @PutMapping("/{entradaId}")
    public Entrada atualizar(@PathVariable("entradaId") Long entradaId,
                             Entrada entrada) {
        return entradaService.atualizar(entradaId, entrada);
    }

    @DeleteMapping("/{entradaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long entradaId) {
        entradaService.excluir(entradaId);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<String> capturar(EntidadeNaoEncontradaException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
