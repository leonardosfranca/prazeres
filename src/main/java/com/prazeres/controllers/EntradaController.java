package com.prazeres.controllers;

import com.prazeres.domain.Entrada;
import com.prazeres.repositories.EntradaRepository;
import com.prazeres.services.EntradaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entradas")
public class EntradaController {
    private final EntradaService entradaService;

    public EntradaController(EntradaService entradaService) {
        this.entradaService = entradaService;
    }

    @GetMapping
    public List<Entrada> listar() {
        return entradaService.listar();
    }


    @GetMapping("/{entradaId}")
    public ResponseEntity<Entrada> buscar(@PathVariable Long entradaId) {
        return entradaService.buscarPorId(entradaId);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Entrada adicionar(@RequestBody Entrada entrada) {
        return entradaService.salvar(entrada);
    }

    @PutMapping("/{entradaId}")
    public ResponseEntity<Entrada> atualizar(@PathVariable Long entradaId,
                                             @RequestBody Entrada entrada) {
        return entradaService.atualizar(entradaId, entrada);
    }
    @DeleteMapping("/{entradaId}")
    public ResponseEntity<Void> deletar(@PathVariable Long entradaId) {
        return entradaService.excluir(entradaId);
    }
}
