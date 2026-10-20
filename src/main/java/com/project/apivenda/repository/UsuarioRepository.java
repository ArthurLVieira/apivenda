package com.project.apivenda.repository;

import com.project.apivenda.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("""
        SELECT DISTINCT u FROM Usuario u
        JOIN FETCH u.roles
        JOIN FETCH u.empresa
        WHERE u.email = :email
        AND u.ativo = TRUE
    """)
    Optional<Usuario> findByUsername(@Param("email") String username);

    Optional<Usuario> findByEmailAndNomeAndCpf(String email, String nome, String cpf);
}
