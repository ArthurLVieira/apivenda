package com.project.apivenda.repository;

import com.project.apivenda.entity.StatusVenda;
import com.project.apivenda.entity.enums.StatusVendaEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StatusVendaRepository extends JpaRepository<StatusVenda, Long> {

    @Query(value = """
        select sv from StatusVenda sv
        where sv.status = :status
    """)
    Optional<StatusVenda> findByStatus(@Param("status") StatusVendaEnum status);
}
