package com.prazeres.repositories;

import com.prazeres.domain.FluxoCaixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FluxoCaixaRepository extends JpaRepository<FluxoCaixa, Long> {

}
