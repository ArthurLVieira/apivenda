package com.project.apivenda.repository;

import com.project.apivenda.entity.ItemVenda;
import com.project.apivenda.entity.enums.StatusVendaEnum;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {

    @Modifying
    @Transactional
    void deleteById(@NonNull Long id);

    @Modifying
    @Transactional
    void deleteByProdutoId(Long produtoId);

    @Query("""
        select it from ItemVenda it
        left join fetch it.venda v
        left join fetch it.produto p
        where v.id = :vendaId
    """)
    Page<ItemVenda> findByVendaId(@Param("vendaId") Long vendaId, Pageable pageable);

    @Query("""
        select it from ItemVenda it
        where it.produto.id = :produtoId
    """)
    boolean existsByProdutoId(@NonNull @Param("produtoId") Long produtoId);

    @Query("""
        select it from ItemVenda it
        left join fetch it.venda v
        where it.produto.id = :produtoId
        and v.status.status = :status
    """)
    boolean existsByProdutoIdAndVenda(
            @NonNull @Param("produtoId") Long produtoId,
            @NonNull @Param("status") StatusVendaEnum status);
}
