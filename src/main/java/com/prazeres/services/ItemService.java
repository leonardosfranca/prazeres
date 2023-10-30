package com.prazeres.services;

import com.prazeres.domain.Item;
import com.prazeres.domain.exceptionhandler.NegocioException;
import com.prazeres.repositories.ItemRepository;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<Item> buscarId(Long itemId) {
        return itemRepository.findById(itemId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
}





    /*public EntrantResponse buscarTodosOsConsumosPorIdEntrada(Long entradaId) {
        Entrada entrada = entradaRepository.findById(entradaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Entrada não encontrada"));
        List<Consumo> consumos = consumoRepository.findAllByEntrada_Id(entradaId);
        if (consumos.isEmpty()) {
            throw new NegocioException("Entrada sem consumo");
        }

        List<ConsumoResponse> consumoResponseList = new ArrayList<>();

        consumos.forEach(consumo -> {
            consumo.setValorTotal(consumo.getQuantidade() * consumo.getItem().getValor());
            ConsumoResponse consumoResponse = new ConsumoResponse(
                    consumo.getId(),
                    consumo.getQuantidade(),
                    consumo.getItem().getDescricao(),
                    consumo.getValorTotal()
            );
            consumoResponseList.add(consumoResponse);
        });

        var valorConsumo = consumoRepository.valorConsumo(entradaId);
        var valorTotal = valorConsumo + entrada.getValorEntrada();
        return new EntradaResponse(
                new EntradaResponse.Quarto(entrada.getQuarto().getNumero()),
                entrada.getDataRegistro(),
                entrada.getHorarioEntrada(),
                entrada.getStatusEntrada(),
                entrada.getTipoPagamento(),
                entrada.getStatusPagamento(),
                consumoResponseList,
                entrada.getValorEntrada(),
                valorConsumo,
                valorTotal

        );
    }*/
