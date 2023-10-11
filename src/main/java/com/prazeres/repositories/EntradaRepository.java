package com.prazeres.repositories;

import com.prazeres.domain.Entrada;
import com.prazeres.enums.StatusEntrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long> {
    List<Entrada> findAllByStatusEntrada(StatusEntrada statusEntrada);
    List<Entrada> findAllByDataRegistro(LocalDate dataRegistro);


}



