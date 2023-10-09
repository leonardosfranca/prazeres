package com.prazeres.controllers;

import com.prazeres.domain.Entrada;
import com.prazeres.domain.exception.EntidadeNaoEncontradaException;
import com.prazeres.domain.exception.NegocioException;
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
    @ResponseStatus(HttpStatus.OK)
    public List<EntradaListaResponse> listarPorEntrada() {
        return entradaService.findAll();
    }

    @GetMapping("/{buscarPorId}")
    @ResponseStatus(HttpStatus.OK)
    public Entrada buscarPorId(@PathVariable Long buscarPorId) {
        return entradaRepository.findById(buscarPorId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Id não encontrado"));
    }

    @GetMapping("/statusEntrada")
    @ResponseStatus(HttpStatus.OK)
    public List<Entrada> listarPorStatus(StatusEntrada statusEntrada) {
        return entradaService.listarPorStatus(statusEntrada);
    }
    @GetMapping("/dataAtual")
    @ResponseStatus(HttpStatus.OK)
    public List<Entrada> listarDataAtual() {
        return entradaService.listarDataAtual();
    }

    @GetMapping("/dataRegistro")
    @ResponseStatus(HttpStatus.OK)
    public List<Entrada> listarPorDataRegistro(LocalDate dataRegistro) {
        return entradaService.listarPorDataRegistro(dataRegistro);
    }

    @GetMapping("/buscarTodosOsConsumosPorIdEntrada/{entradaId}")
    @ResponseStatus(HttpStatus.OK)
    public EntradaResponse buscarTodosOsConsumosPorIdEntrada(@PathVariable Long entradaId) {
        return entradaService.buscarPorId(entradaId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Entrada salvar(Entrada entrada) {
        return entradaService.salvar(entrada);
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

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<String> capturar(EntidadeNaoEncontradaException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<String> capturar(NegocioException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
