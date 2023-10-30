package com.prazeres.services;

import com.prazeres.domain.Consumo;
import com.prazeres.domain.Entrada;
import com.prazeres.domain.exceptionhandler.EntidadeNaoEncontradaException;
import com.prazeres.domain.exceptionhandler.NegocioException;
import com.prazeres.domain.record.ConsumoResponse;
import com.prazeres.repositories.ConsumoRepository;
import com.prazeres.repositories.EntradaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConsumoService {

    private final ConsumoRepository consumoRepository;
    private final EntradaRepository entradaRepository;

    public ConsumoService(ConsumoRepository consumoRepository, EntradaRepository entradaRepository) {
        this.consumoRepository = consumoRepository;
        this.entradaRepository = entradaRepository;
    }

    public List<ConsumoResponse> findAll() {
        var listaConsumo = consumoRepository.findAll();
        List<ConsumoResponse> consumo = new ArrayList<>();
        listaConsumo.forEach(listaConsumo1 -> {
            ConsumoResponse consumoListaResponse = new ConsumoResponse(
                    listaConsumo1.getId(),
                    listaConsumo1.getQuantidade(),
                    listaConsumo1.getItem().getDescricao(),
                            listaConsumo1.getItem().getValor(),
                    subTotal(listaConsumo1));
            consumo.add(consumoListaResponse);
        });

        return consumo;
    }


    public Consumo salvar(Consumo consumo, Entrada entrada) {
        if (!entradaRepository.existsById(entrada.getId())) {
            throw new EntidadeNaoEncontradaException("Entrada não encontrada");
        }
        if (consumo.getEntrada().getId() == null || consumo.getEntrada().getId().describeConstable().isEmpty()) {
            throw new NegocioException("Entrada não informada");
        }

        consumo.setValor(subTotal(consumo));
        return consumoRepository.save(consumo);
    }

    public List<ConsumoResponse> buscarConsumoPorId(Long consumoId) {
        var consumo = consumoRepository.findAllByEntrada_Id(consumoId);

        if (consumo.isEmpty()) {
            throw new NegocioException("Não houve consumo");
        }

        List<ConsumoResponse> consumoList = new ArrayList<>();

        consumo.forEach(consumo1 -> {
            ConsumoResponse consumoResponse = new ConsumoResponse(
                    consumo1.getId(),
            consumo1.getQuantidade(),
            consumo1.getItem().getDescricao(),
            consumo1.getValor(),
            consumo1.getValorTotal());
            consumoList.add(consumoResponse);
        });
        return consumoList;
    }
    public Double subTotal(Consumo consumo) {
        return consumo.getQuantidade() * consumo.getItem().getValor();
    }

    public void excluir(Long consumoId) {
        Consumo buscarConsumo = consumoRepository.findById(consumoId)
                .orElseThrow(() -> new NegocioException("Consumo não encontrado"));
        consumoRepository.deleteById(buscarConsumo.getId());
    }



    //TODO: findallquartos do menor pro maior
    //TODO: ao criar entrada seta automaticamente o status EM_ANDAMENTO
    /*TODO: quando o statusPAGAMENTO for PAGO, setar o status da entrada automaticamente pra finalizada,
    e se ela for finalizada nao pode mais ser alterada de jeito nenhum*/
    //TODO: no response de atualizar entrada colocar o valor do quarto e  valor total do consumo em variaveis diferentes


}
