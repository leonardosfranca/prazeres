package com.prazeres.controllers;

import com.prazeres.domain.Quarto;
import com.prazeres.domain.exceptionhandler.NegocioException;
import com.prazeres.enums.StatusQuarto;
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

    @GetMapping("/statusQuarto")
    @ResponseStatus(HttpStatus.OK)
    public List<Quarto> listarPorStatus(StatusQuarto statusQuarto) {
        return quartoService.listarPorStatus(statusQuarto);
    }

    @GetMapping("/{quartoId}")
    public ResponseEntity<Quarto> buscarPorId(@PathVariable Long quartoId) {
        return quartoService.buscar(quartoId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Quarto adicionar(@RequestBody Quarto quarto) {
        return quartoService.salvar(quarto);
    }

    @PutMapping("/{quartoId}")
    public ResponseEntity<Quarto> atualizar(@PathVariable Long quartoId,
                                            @RequestBody Quarto quarto) {
        return quartoService.atualizar(quartoId, quarto);
    }

    @PostMapping("/{quartoId}")
    public void liberarQuarto(@PathVariable Long quartoId) {
        quartoService.fazerCheckOut(quartoId);
    }

    @DeleteMapping("/{quartoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> excluir(@PathVariable Long quartoId) {
        return quartoService.remover(quartoId);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<String> capturar(NegocioException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
