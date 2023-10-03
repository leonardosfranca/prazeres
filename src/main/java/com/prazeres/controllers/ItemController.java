package com.prazeres.controllers;

import com.prazeres.domain.Item;
import com.prazeres.services.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<Item> listar() {
        return itemService.listar();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Item adicionar(@RequestBody Item item) {
        return itemService.adicionar(item);
    }
}
