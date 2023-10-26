package com.prazeres.repositories;

import com.prazeres.domain.Consumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ConsumoRepository extends JpaRepository<Consumo, Long> {
    List<Consumo> findAllByEntrada_Id(Long consumoId);
    @Query(value = "select sum(c.valor)  from Consumo c  where c.entrada.id =:id")
    Float valorConsumo (Long id);

}
