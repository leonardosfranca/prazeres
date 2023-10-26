package com.prazeres.controllers;

import com.prazeres.domain.FluxoCaixa;
import com.prazeres.domain.exceptionhandler.NegocioException;
import com.prazeres.services.FluxoCaixaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @ResponseStatus(HttpStatus.CREATED)
    public FluxoCaixa salvar(FluxoCaixa entrada) {
        return fluxoCaixaService.salvarFluxoCaixa(entrada);
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

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<String> capturar(NegocioException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
