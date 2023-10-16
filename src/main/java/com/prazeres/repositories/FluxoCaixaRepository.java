package com.prazeres.repositories;

import com.prazeres.domain.FluxoCaixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FluxoCaixaRepository extends JpaRepository<FluxoCaixa, Long> {

    @Query(value = "select max (fc.valor_total) from fluxo_caixa fc", nativeQuery = true)
    Double valorCaixa();

}
