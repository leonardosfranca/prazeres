package com.prazeres.repositories;

import com.prazeres.domain.Consumo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsumoRepository extends JpaRepository<Consumo, Long> {
    List<Consumo> findConsumoByEntrada_Id(Long consumoId);
}
