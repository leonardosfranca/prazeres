package com.prazeres.repositories;

import com.prazeres.domain.Entrada;
import com.prazeres.domain.record.EntradaListaResponse;
import com.prazeres.enums.StatusEntrada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface EntradaRepository extends JpaRepository<Entrada, Long> {
    List<Entrada> findAllByStatusEntrada(StatusEntrada statusEntrada);
    List<Entrada> findAllByDataRegistro(LocalDate dataRegistro);

}



