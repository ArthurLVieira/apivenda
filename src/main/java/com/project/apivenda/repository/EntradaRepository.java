package com.project.apivenda.repository;

import com.project.apivenda.entity.Empresa;
import com.project.apivenda.entity.Entrada;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntradaRepository extends JpaRepository<Entrada,Long> {

    @Query("""
        select e from Entrada e
        left join e.fornecedor f
        where e.empresa = :empresa
        and (concat(e.numero , '') ilike concat('%', :search, '%')
        or lower(f.nomeCompleto) ilike concat('%', :search, '%')
        or f.cpf ILIKE CONCAT('%', :search, '%'))
    """)
    Page<Entrada> search(
            @Param("search") String search,
            @Param("empresa") Empresa empresa,
            Pageable pageable
    );

    Page<Entrada> findByEmpresa(Empresa empresa, Pageable pageable);
}
