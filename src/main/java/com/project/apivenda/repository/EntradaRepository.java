package com.project.apivenda.repository;

import com.project.apivenda.entity.Empresa;
import com.project.apivenda.entity.Entrada;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EntradaRepository extends JpaRepository<Entrada,Long> {

    @Query("""
        select e from Entrada e
        left join e.fornecedor f
        where e.empresa = :empresa
        and (cast(e.numero as string) ILIKE CONCAT('%', :search, '%')
        or lower(f.nomeCompleto) ILIKE CONCAT('%', :search, '%')
        or f.cpf ILIKE CONCAT('%', :search, '%'))
    """)
    Page<Entrada> search(
            String search,
            Empresa empresa,
            Pageable pageable
    );

    Page<Entrada> findByEmpresa(Empresa empresa, Pageable pageable);
}
