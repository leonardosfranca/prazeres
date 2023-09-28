package com.prazeres.repositories;

import com.prazeres.domain.Entrada;
import com.prazeres.enums.StatusEntrada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface EntradaRepository extends JpaRepository<Entrada, Long> {
    Optional<Entrada> findByQuarto(Long id);
    List<Entrada> findAllByStatusEntrada(StatusEntrada statusEntrada);
    List<Entrada> findAllByDataRegistro(LocalDate dataRegistro);
    List<Entrada> findAllByHorarioEntradaAndDataRegistro(LocalTime horarioEntrada, LocalDate dataRegistro);

}
