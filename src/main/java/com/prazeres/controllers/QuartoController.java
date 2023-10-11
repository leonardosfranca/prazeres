package com.prazeres.controllers;

import com.prazeres.domain.Quarto;
import com.prazeres.domain.exceptionhandler.NegocioException;
import com.prazeres.enums.StatusQuarto;
import com.prazeres.services.QuartoService;
import org.springframework.data.repository.query.Param;
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

    @GetMapping("/{quartoId}")
    @ResponseStatus(HttpStatus.OK)
    public Quarto buscarPorId(@PathVariable Long quartoId) {
        return quartoService.buscarPorId(quartoId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Quarto salvar(@RequestBody Quarto quarto) {
        return quartoService.salvar(quarto);
    }

    @PutMapping("/{quartoId}")
    public Quarto atualizar(@PathVariable Long quartoId,
                            Quarto quarto) {
        return quartoService.atualizar(quartoId, quarto);
    }

    @DeleteMapping("/{quartoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long quartoId) {
        quartoService.excluir(quartoId);
    }

    @GetMapping("/statusQuarto")
    @ResponseStatus(HttpStatus.OK)
    public List<Quarto> listarPorStatus(StatusQuarto statusQuarto) {
        return quartoService.listarPorStatus(statusQuarto);
    }

    @PostMapping("/{quartoId}")
    public void liberarQuarto(@PathVariable Long quartoId) {
         quartoService.fazerCheckOut(quartoId);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<String> capturar(NegocioException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
