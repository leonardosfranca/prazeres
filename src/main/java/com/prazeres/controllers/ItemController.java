package com.prazeres.controllers;

import com.prazeres.domain.Item;
import com.prazeres.domain.exceptionhandler.NegocioException;
import com.prazeres.domain.record.ItemResponse;
import com.prazeres.services.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<ItemResponse> listar() {
        return itemService.listar();
    }

    @GetMapping("/{itemId}")
    public ItemResponse buscarId(@PathVariable Long itemId) {
        return itemService.buscarId(itemId);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Item adicionar(@RequestBody Item item) {
        return itemService.adicionar(item);
    }

    @PutMapping("/{itemId}")
    public ItemResponse atualizar(@PathVariable Long itemId,
                                  @RequestBody Item item) {
        return itemService.atualizar(itemId, item);
    }

    @DeleteMapping("/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long itemId) {
        itemService.excluir(itemId);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<String> capturar(NegocioException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
