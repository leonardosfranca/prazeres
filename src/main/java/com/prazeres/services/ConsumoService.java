package com.prazeres.services;

import com.prazeres.domain.Consumo;
import com.prazeres.domain.exceptionhandler.NegocioException;
import com.prazeres.domain.record.ConsumoResponse;
import com.prazeres.repositories.ConsumoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConsumoService {

    private final ConsumoRepository consumoRepository;

    public ConsumoService(ConsumoRepository consumoRepository) {
        this.consumoRepository = consumoRepository;
    }

    public List<ConsumoResponse> findAll() {
        var listaConsumo = consumoRepository.findAll();
        List<ConsumoResponse> consumo = new ArrayList<>();
        listaConsumo.forEach(listaConsumo1 -> {
            ConsumoResponse consumoListaResponse = new ConsumoResponse(
                    listaConsumo1.getQuantidade(),
                    listaConsumo1.getItem().getDescricao(),
                            listaConsumo1.getValor());
            consumo.add(consumoListaResponse);
        });

        return consumo;
    }


    public Consumo salvar(Consumo consumo) {
        if (consumo.getEntrada().getId() == null || consumo.getEntrada().getId().describeConstable().isEmpty()) {
            throw new NegocioException("Entrada não informada");
        }
        var subTotal = consumo.getQuantidade() * consumo.getItem().getValor();
        consumo.setValor(subTotal);
        return consumoRepository.save(consumo);
    }

    public List<ConsumoResponse> buscarConsumoPorId(Long entradaId) {
        var consumo = consumoRepository.findAllByEntrada_Id(entradaId);

        if (consumo.isEmpty()) {
            throw new NegocioException("Não houve consumo");
        }

        List<ConsumoResponse> consumoList = new ArrayList<>();
        consumo.forEach(consumo1 -> {
            ConsumoResponse consumoResponse = new ConsumoResponse(
            consumo1.getQuantidade(),
            consumo1.getItem().getDescricao(),
            consumo1.getValor());
            consumoList.add(consumoResponse);
        });
        return consumoList;
    }

    public void excluir(Long consumoId) {
        Consumo buscarConsumo = consumoRepository.findById(consumoId)
                .orElseThrow(() -> new NegocioException("Consumo não encontrado"));
        consumoRepository.deleteById(buscarConsumo.getId());
    }

}
