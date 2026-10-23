package com.project.apivenda.repository;

import com.project.apivenda.entity.StatusEntrada;
import com.project.apivenda.entity.enums.StatusEntradaEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StatusEntradaRepository extends JpaRepository<StatusEntrada,Long> {

    @Query("""
        select se from StatusEntrada se
        where se.status = :status
    """)
    Optional<StatusEntrada> findByStatus(@Param("status") StatusEntradaEnum status);
}
