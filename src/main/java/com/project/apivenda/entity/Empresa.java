package com.project.apivenda.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "empresa", schema = "public")
public class Empresa implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "nome_razao", nullable = false)
    private String nomeRazao;

    @Column(name = "cpf_cnpj",  nullable = false, unique = true)
    private String cpfCnpj;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "email", nullable = false, unique = true)
    private String email;
}
