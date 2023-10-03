package com.prazeres.repositories;

import com.prazeres.domain.Consumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ConsumoRepository extends JpaRepository<Consumo, Long> {
    List<Consumo> findConsumoByEntradaId(Long consumoId);
    @Query(value = "select sum(sub_total)  from consumo c   ", nativeQuery = true)
    Float valorConsumo ();
}
