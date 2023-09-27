package com.prazeres.controllers;

import com.prazeres.domain.Quarto;
import com.prazeres.services.QuartoService;
import org.springframework.http.HttpStatus;
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
        return quartoService.buscar(buscaPorId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Quarto adicionar(@RequestBody Quarto quarto) {
        return quartoService.salvar(quarto);
    }
}
