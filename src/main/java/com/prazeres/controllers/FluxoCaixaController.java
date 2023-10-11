package com.prazeres.controllers;

import com.prazeres.domain.FluxoCaixa;
import com.prazeres.services.FluxoCaixaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fluxoCaixas")
public class FluxoCaixaController {

    private final FluxoCaixaService fluxoCaixaService;
    public FluxoCaixaController(FluxoCaixaService fluxoCaixaService) {
        this.fluxoCaixaService = fluxoCaixaService;
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FluxoCaixa> listar() {
        return fluxoCaixaService.listar();
    }

    @GetMapping("/{fluxoCaixaId}")
    public FluxoCaixa buscarPorId(@PathVariable Long fluxoCaixaId) {
        return fluxoCaixaService.buscarPorId(fluxoCaixaId);
    }

    @PostMapping
    public FluxoCaixa criar(FluxoCaixa fluxoCaixa) {
        return fluxoCaixaService.criar(fluxoCaixa);
    }

    @PutMapping("/{fluxoCaixaId}")
    public FluxoCaixa atualizar(Long fluxoCaixaId, FluxoCaixa fluxoCaixa) {
        return fluxoCaixaService.atualizar(fluxoCaixaId, fluxoCaixa);
    }

    @DeleteMapping("/{fluxoCaixaId}")
    public void remover(Long fluxoCaixaId) {
        fluxoCaixaService.excluir(fluxoCaixaId);
    }
}
