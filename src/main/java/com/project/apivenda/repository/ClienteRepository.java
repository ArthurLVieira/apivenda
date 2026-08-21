package com.project.apivenda.repository;

import com.project.apivenda.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("""
        select c from Cliente c
        where (c.nomeCompleto ILIKE CONCAT('%', :search, '%')
        or c.cpf ILIKE CONCAT('%', :search, '%'))
    """)
    Page<Cliente> search(String search, Pageable pageable);

    Optional<Cliente> findByNomeCompletoAndCpf(String nomeCompleto, String cpf);

    Optional<Cliente> findByCpf(String cpf);
}
