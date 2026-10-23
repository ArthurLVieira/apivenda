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
        and (c.nomeRazao ILIKE CONCAT('%', :search, '%')
        or c.cpfCnpj ILIKE CONCAT('%', :search, '%'))
    """)
    Page<Cliente> search(String search, Empresa empresa, Pageable pageable);

    Page<Cliente> findAllByEmpresa(Empresa empresa, Pageable pageable);

    Optional<Cliente> findByNomeRazaoAndCpfCnpjAndEmpresa(String nomeRazao, String cpfCnpj, Empresa empresa);

    Optional<Cliente> findByCpfCnpjAndEmpresa(String cpfCnpj, Empresa empresa);
}
