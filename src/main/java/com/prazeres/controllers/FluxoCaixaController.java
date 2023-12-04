package com.prazeres.controllers;

import com.prazeres.domain.FluxoCaixa;
import com.prazeres.domain.exceptionhandler.NegocioException;
import com.prazeres.services.FluxoCaixaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimentacoes")
public class FluxoCaixaController {

    private final FluxoCaixaService fluxoCaixaService;
    public FluxoCaixaController(FluxoCaixaService fluxoCaixaService) {
        this.fluxoCaixaService = fluxoCaixaService;
    }
    @GetMapping
    public List<FluxoCaixa> listarTodasMovimentacoes() {
        return fluxoCaixaService.listarTodasMovimentacoes();
    }

    @GetMapping("/{fluxoCaixaId}")
    public FluxoCaixa buscarMovimentacoesPorId(@PathVariable Long fluxoCaixaId) {
        return fluxoCaixaService.buscarMovimentacoesPorId(fluxoCaixaId);
    }
    @GetMapping("/consultaDeBaixoParaCima")
    public List<FluxoCaixa> consultaDeBaixoParaCima() {
        return fluxoCaixaService.consultarDadosDeBaixoParaCima();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FluxoCaixa criarMovimentacao(FluxoCaixa entrada) {
        return fluxoCaixaService.criarMovimentacao(entrada);
    }

    @GetMapping("/entrada")
    public List<FluxoCaixa> listarMovimentacoesEntrada() {
        return fluxoCaixaService.listarMovimentacoesEntrada();
    }

    @GetMapping("/saida")
    public List<FluxoCaixa> listarMovimentacoesSaida() {
        return fluxoCaixaService.listarMovimentacoesSaida();
    }

    @GetMapping("/total")
    public double calcularTotal() {
         fluxoCaixaService.listarMovimentacoesEntrada();
         fluxoCaixaService.listarMovimentacoesSaida();
        return fluxoCaixaService.calcularTotal();
    }
    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<String> capturar(NegocioException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
