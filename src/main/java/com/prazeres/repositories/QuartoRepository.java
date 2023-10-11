package com.prazeres.repositories;

import com.prazeres.domain.Quarto;
import com.prazeres.enums.StatusQuarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface QuartoRepository extends JpaRepository<Quarto, Long> {
    List<Quarto> findAllByStatusQuarto(StatusQuarto statusQuarto);
}
