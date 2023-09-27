package com.prazeres.repositories;

import com.prazeres.domain.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EntradaRepository extends JpaRepository<Entrada, Long> {
    Optional<Entrada> findByQuarto(Long id);
}
