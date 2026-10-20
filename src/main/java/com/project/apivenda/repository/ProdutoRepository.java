package com.project.apivenda.repository;

import com.project.apivenda.entity.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("""
        select p from Produto p
        where (p.descricao ILIKE CONCAT('%', :search, '%')
        and p.codigoDeBarras ILIKE CONCAT('%', :search, '%'))
    """)
    Page<Produto> search(String search, Pageable pageable);

    Optional<Produto> findByDescricaoAndCodigoDeBarras(String descricao, String codigoDeBarras);

    Optional<Produto> findByCodigoDeBarras(String codigoDeBarras);
}
