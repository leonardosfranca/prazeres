package com.prazeres.controllers;

import com.prazeres.domain.Entrada;
import com.prazeres.repositories.EntradaRepository;
import com.prazeres.services.EntradaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class EntradaController {

    private EntradaService entradaService;

    public EntradaController(EntradaService entradaService) {
        this.entradaService = entradaService;
    }

//    @GetMapping
//    public List<Entrada> listar() {
//        return entradaService.findAll();
//    }

    @GetMapping("/{id}")
    public Entrada findById(@PathVariable("id") Long id) {
        return entradaService.findById(id);
    }
}
