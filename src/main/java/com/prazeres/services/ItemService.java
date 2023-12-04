package com.prazeres.services;

import com.prazeres.domain.Item;
import com.prazeres.domain.Quarto;
import com.prazeres.domain.exceptionhandler.EntidadeNaoEncontradaException;
import com.prazeres.domain.exceptionhandler.NegocioException;
import com.prazeres.domain.record.ItemResponse;
import com.prazeres.enums.StatusQuarto;
import com.prazeres.repositories.ItemRepository;
import com.prazeres.repositories.QuartoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final QuartoRepository quartoRepository;

    public ItemService(ItemRepository itemRepository, QuartoRepository quartoRepository) {
        this.itemRepository = itemRepository;
        this.quartoRepository = quartoRepository;
    }

    public List<ItemResponse> listar() {
        List<Item> itens = itemRepository.findAll();
        List<ItemResponse> itemResponses = new ArrayList<>();

        for (Item item : itens) {
            ItemResponse itemResponse = new ItemResponse(
                    item.getId(),
                    item.getDescricao(),
                    item.getValor()
            );
            itemResponses.add(itemResponse);
        }

        return itemResponses;
    }


    public ItemResponse buscarId(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(()-> new EntidadeNaoEncontradaException("Item não encontrado"));
        ItemResponse itemResponse = new ItemResponse(
                item.getId(),
                item.getDescricao(),
                item.getValor()
        );

        return itemResponse;
    }

    public Item adicionar(Item itemId) {
        return itemRepository.save(itemId);
    }

    public ItemResponse atualizar(Long itemId, Item itemRequest) {
        Item itemExistente = itemRepository.findById(itemId)
                .orElseThrow(() -> new NegocioException("Item não encontrado"));

        if (itemEmUso(itemExistente)) {
            throw new NegocioException("O item está em uso e não pode ser atualizado");
        }

        // Atualize as propriedades do item existente com base na solicitação
        itemExistente.setDescricao(itemRequest.getDescricao());
        itemExistente.setValor(itemRequest.getValor());

        itemRepository.save(itemExistente);

        // Crie uma instância de ItemResponse com os dados atualizados e retorne
        ItemResponse itemResponse = new ItemResponse(
                itemExistente.getId(),
                itemExistente.getDescricao(),
                itemExistente.getValor()
        );

        return itemResponse;
    }

    private boolean itemEmUso(Item item) {
        Quarto quarto = item.getQuarto(); // Supondo que o item está associado a um quarto

        if (quarto != null && quarto.getStatusQuarto() == StatusQuarto.OCUPADO) {
            return true; // O item está associado a um quarto ocupado
        }

        return false; // O item não está associado a um quarto ocupado
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
