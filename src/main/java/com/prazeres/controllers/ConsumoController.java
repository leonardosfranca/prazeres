package com.prazeres.controllers;

import com.prazeres.domain.Consumo;
import com.prazeres.domain.Entrada;
import com.prazeres.domain.exceptionhandler.NegocioException;
import com.prazeres.domain.record.ConsumoResponse;
import com.prazeres.services.ConsumoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consumos")
public class ConsumoController {

    private final ConsumoService consumoService;
    public ConsumoController(ConsumoService consumoService) {
        this.consumoService = consumoService;
    }
    @GetMapping
    public List<ConsumoResponse> listar() {
        return consumoService.findAll();
    }

    @GetMapping("/findConsumoBy/{entradaId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ConsumoResponse> buscar(@PathVariable Long entradaId) {
        return consumoService.buscarConsumoPorId(entradaId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Consumo adicionar(Consumo consumo, Entrada entrada) {
        return consumoService.salvar(consumo, entrada);
    }

    @DeleteMapping("/{consumoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long consumoId) {
        consumoService.excluir(consumoId);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<String> capturar(NegocioException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
