package com.project.apivenda.repository;

import com.project.apivenda.entity.Venda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {

    @Query("""
        SELECT v FROM Venda v
        LEFT JOIN FETCH v.status s
        LEFT JOIN FETCH v.cliente c
        WHERE (v.status.id IN :statusIds
                AND LOWER(c.nomeCompleto) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(c.cpf) LIKE LOWER(CONCAT('%', :search, '%')))
    """)
    Page<Venda> searchByStatusIds(
            @Param("search") String search,
            @Param("statusIds") List<Long> statusIds,
            Pageable pageable
    );

    @Query("""
        SELECT v FROM Venda v
        LEFT JOIN FETCH v.status s
        LEFT JOIN FETCH v.cliente c
        WHERE v.status.id = :statusId
    """)
    Page<Venda> findByStatusId(
            @Param("statusId") Long statusId,
            Pageable pageable
    );

    @Query("""
        SELECT v FROM Venda v
        LEFT JOIN FETCH v.status s
        LEFT JOIN FETCH v.cliente c
        WHERE v.status.id IN :statusIds
    """)
    Page<Venda> findByStatusIdIn(
            @Param("statusIds") List<Long> statusIds,
            Pageable pageable
    );

    @Query("""
        SELECT v FROM Venda v
        LEFT JOIN FETCH v.status s
        LEFT JOIN FETCH v.cliente c
        WHERE (LOWER(c.nomeCompleto) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(c.cpf) LIKE LOWER(CONCAT('%', :search, '%')))
    """)
    Page<Venda> search(
            @Param("search") String search,
            Pageable pageable
    );

    @Query("""
        SELECT v FROM Venda v
        LEFT JOIN FETCH v.status s
        LEFT JOIN FETCH v.cliente c
    """)
    Page<Venda> findAllWithRelations(Pageable pageable);


}
