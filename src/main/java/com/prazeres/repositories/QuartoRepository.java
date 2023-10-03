package com.prazeres.repositories;

import com.prazeres.domain.Entrada;
import com.prazeres.domain.Quarto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuartoRepository extends JpaRepository<Quarto, Long> {
    Optional<Quarto> findByQuarto(Long id);
}
