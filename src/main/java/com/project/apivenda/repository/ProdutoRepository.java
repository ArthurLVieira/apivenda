package com.project.apivenda.repository;

import com.project.apivenda.entity.Empresa;
import com.project.apivenda.entity.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("""
        select p from Produto p
        where p.empresa = :empresa
        and (p.descricao ILIKE CONCAT('%', :search, '%')
        or p.codigoDeBarras ILIKE CONCAT('%', :search, '%'))
    """)
    Page<Produto> search(String search, Empresa empresa, Pageable pageable);

    Page<Produto> findByEmpresa(Empresa empresa, Pageable pageable);

    Optional<Produto> findByDescricaoAndCodigoDeBarrasAndEmpresa(String descricao, String codigoDeBarras, Empresa empresa);

    Optional<Produto> findByCodigoDeBarrasAndEmpresa(String codigoDeBarras, Empresa empresa);
}
