package com.prazeres.repositories;

import com.prazeres.domain.FluxoCaixa;
import com.prazeres.enums.TipoMovimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FluxoCaixaRepository extends JpaRepository<FluxoCaixa, Long> {

    @Query(value = "select max (fc.valor_total) from fluxo_caixa fc", nativeQuery = true)
    Double valorCaixa();

    List<FluxoCaixa> findByTipo(TipoMovimentacao tipoMovimentacao);

    @Query("SELECT e FROM FluxoCaixa e ORDER BY e.id DESC")
    List<FluxoCaixa> findAllOrderByCampoDesc();
}
