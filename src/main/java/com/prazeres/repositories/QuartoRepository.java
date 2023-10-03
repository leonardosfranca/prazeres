package com.prazeres.repositories;

import com.prazeres.domain.Entrada;
import com.prazeres.domain.Quarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface QuartoRepository extends JpaRepository<Quarto, Long> {

}
