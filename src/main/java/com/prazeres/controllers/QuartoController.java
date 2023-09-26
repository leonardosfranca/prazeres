package com.prazeres.controllers;

import com.prazeres.domain.Quarto;
import com.prazeres.services.QuartoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quartos")
public class QuartoController {
    private final QuartoService quartoService;

    public QuartoController(QuartoService quartoService) {
        this.quartoService = quartoService;
    }

    @GetMapping("/{buscaPorId}")
    public Quarto buscaPorId(@PathVariable Long buscaPorId) {
        return quartoService.buscar(buscaPorId);
    }
}
