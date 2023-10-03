package com.prazeres.controllers;

import com.prazeres.domain.Consumo;
import com.prazeres.domain.record.ConsumoResponse;
import com.prazeres.services.ConsumoService;
import org.springframework.http.HttpStatus;
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
    public List<Consumo> listar(Consumo consumo) {
        return consumoService.listar(consumo);
    }

    @GetMapping("/findByEntradaId/{entradaId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ConsumoResponse> buscar(@PathVariable Long entradaId) {
        return consumoService.findConsumoByEntrdaId(entradaId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Consumo adicionar(Consumo consumo) {
        return consumoService.salvar(consumo);
    }

    @DeleteMapping("/{consumoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long consumoId) {
        consumoService.excluir(consumoId);
    }



}
