package com.prazeres.services;

import com.prazeres.domain.Consumo;
import com.prazeres.domain.exception.EntidadeNaoEncontradaException;
import com.prazeres.domain.exception.NegocioException;
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

    public List<Consumo> listar(Consumo consumo) {
        return consumoRepository.findAll();
    }

    public Consumo salvar(Consumo consumo) {
        if (consumo.getEntrada().getId() == null || consumo.getEntrada().getId().describeConstable().isEmpty()) {
            throw new NegocioException("Entrada não informada");
        }
        var subTotal = consumo.getQuantidade() * consumo.getItem().getValor();
        consumo.setSubTotal(subTotal);
        return consumoRepository.save(consumo);
    }

    public List<ConsumoResponse> findConsumoByEntrdaId(Long entradaId) {
        var consumo = consumoRepository.findConsumoByEntrada_Id(entradaId);
        List<ConsumoResponse> consumoResponseList = new ArrayList<>();
        consumo.forEach(consumo1 -> {
            ConsumoResponse consumoResponse = new ConsumoResponse(
                    new ConsumoResponse.Entrada(consumo1.getEntrada().getId(),
                            consumo1.getEntrada().getQuarto().getCapacidadePessoas()),
                            consumo1.getQuantidade(),
                            consumo1.getItem().getDescricao(),
                            consumo1.getSubTotal());
                            consumoResponseList.add(consumoResponse);
        });
        return consumoResponseList;
    }

    public void excluir(Long consumoId) {
        Consumo buscarConsumo = consumoRepository.findById(consumoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Consumo não encontrado"));
        consumoRepository.deleteById(buscarConsumo.getId());
    }
}