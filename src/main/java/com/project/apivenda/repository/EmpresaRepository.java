package com.project.apivenda.repository;

import com.project.apivenda.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    Optional<Empresa>  findByNomeRazaoAndCpfCnpjAndEmail(String nomeRazao, String cpfCnpj, String email);

    Optional<Empresa> findByCpfCnpj(String cpfCnpj);
}
