package com.prazeres.controllers;

import com.prazeres.domain.Quarto;
import com.prazeres.domain.exception.EntidadeNaoEncontradaException;
import com.prazeres.services.QuartoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quartos")
public class QuartoController {
    private final QuartoService quartoService;

    public QuartoController(QuartoService quartoService) {
        this.quartoService = quartoService;
    }

    @GetMapping
    public List<Quarto> listar() {
        return quartoService.listar();
    }

    @GetMapping("/{buscaPorId}")
    public Quarto buscaPorId(@PathVariable Long buscaPorId) {
        return quartoService.buscaPorId(buscaPorId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Quarto salvar(@RequestBody Quarto quarto) {
        return quartoService.salvar(quarto);
    }

    @PutMapping("/{quartoId}")
    public ResponseEntity<Quarto> atualizar(@PathVariable Long quartoId,
                                            @RequestBody Quarto quarto) {
        return quartoService.atualizar(quartoId, quarto);
    }

    @DeleteMapping("/{quartoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long quartoId) {
        quartoService.excluir(quartoId);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<String> capturar(EntidadeNaoEncontradaException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
