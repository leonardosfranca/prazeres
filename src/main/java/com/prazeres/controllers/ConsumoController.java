package com.prazeres.controllers;

import com.prazeres.domain.Consumo;
import com.prazeres.domain.exception.NegocioException;
import com.prazeres.services.ConsumoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consumo")
public class ConsumoController {

    private final ConsumoService consumoService;

    public ConsumoController(ConsumoService consumoService) {
        this.consumoService = consumoService;
    }

    @GetMapping("/{consumoId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Consumo> buscar(@PathVariable Long consumoId) {
        return consumoService.buscarPorId(consumoId);
    }

    @DeleteMapping("/{consumoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deletar(@PathVariable Long consumoId) {
        return consumoService.excluir(consumoId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Consumo adicionar(Consumo consumo) {
        return consumoService.salvar(consumo);
    }
}
