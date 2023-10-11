package com.prazeres.services;

import com.prazeres.domain.Item;
import com.prazeres.domain.exceptionhandler.NegocioException;
import com.prazeres.repositories.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> listar() {
        return itemRepository.findAll();
    }

    public Item adicionar(Item itemId) {
        return itemRepository.save(itemId);
    }

    public Item atualizar(Long itemId, Item itemRequest) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NegocioException("Item não encontrado"));
        Item itemAtualizado = new Item(
                item.getId(),
                itemRequest.getDescricao(),
                itemRequest.getValor()
        );
        itemRepository.save(itemAtualizado);
        return item;
    }

    public void excluir(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    public Item buscarId(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new NegocioException("Item não encontrado"));
    }
}
