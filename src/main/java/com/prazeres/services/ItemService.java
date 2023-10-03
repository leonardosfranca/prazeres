package com.prazeres.services;

import com.prazeres.domain.Item;
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
}
