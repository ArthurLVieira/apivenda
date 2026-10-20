package com.project.apivenda.repository;

import com.project.apivenda.entity.Cliente;
import com.project.apivenda.entity.Empresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("""
        select c from Cliente c
        where c.empresa = :empresa
        and (c.nomeCompleto ILIKE CONCAT('%', :search, '%')
        or c.cpf ILIKE CONCAT('%', :search, '%'))
    """)
    Page<Cliente> search(String search, Empresa empresa, Pageable pageable);

    @Query("""
        select c from Cliente c
        where c.empresa = :empresa
    """)
    Page<Cliente> findAll(Empresa empresa, Pageable pageable);

    Optional<Cliente> findByNomeCompletoAndCpfAndEmpresa(String nomeCompleto, String cpf, Empresa empresa);

    Optional<Cliente> findByCpfAndEmpresa(String cpf, Empresa empresa);
}
