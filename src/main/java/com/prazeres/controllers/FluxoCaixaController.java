package com.prazeres.controllers;

import com.prazeres.domain.FluxoCaixa;
import com.prazeres.services.FluxoCaixaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<FluxoCaixa> buscarPorId(@PathVariable Long fluxoCaixaId) {
        return fluxoCaixaService.buscarPorId(fluxoCaixaId);
    }

    @PostMapping
    public FluxoCaixa criar(FluxoCaixa fluxoCaixa) {
        return fluxoCaixaService.criar(fluxoCaixa);
    }
    @GetMapping("/total")
    public Map<String, Double> obterTotal() {
        Map<String, Double> total = new HashMap<>();
        total.put("entradaTotal", fluxoCaixaService.entradaTotal());
        total.put("saidaTotal", fluxoCaixaService.saidaTotal());
        total.put("saldoTotal", fluxoCaixaService.saldoTotal());
        return total;
    }

    @PutMapping("/{fluxoCaixaId}")
    public FluxoCaixa atualizar(@PathVariable Long fluxoCaixaId, FluxoCaixa fluxoCaixa) {
        return fluxoCaixaService.atualizar(fluxoCaixaId, fluxoCaixa);
    }

    @DeleteMapping("/{fluxoCaixaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long fluxoCaixaId) {
        fluxoCaixaService.excluir(fluxoCaixaId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteAll(FluxoCaixa fluxoCaixa) {
        fluxoCaixaService.exlcuirTudo(fluxoCaixa);
    }

}
