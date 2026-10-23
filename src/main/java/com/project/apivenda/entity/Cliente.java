package com.project.apivenda.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "cliente", schema = "public")
public class Cliente implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "nome_razao",  nullable = false)
    private String nomeRazao;

    @Column(name = "cpf_cnpj", nullable = false, unique = true)
    private String cpfCnpj;

    @Column(name = "nome_fantazia")
    private String nomeFantazia;

    @Column(name = "data_nascimento_criacao")
    private LocalDate dataNascimentoCriacao;

    @Column(name = "fornecedor")
    private Boolean fornecedor = false;

    @Column(name = "status", nullable = false)
    private boolean status = true;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Venda> vendas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_cadastro_id")
    private Usuario usuarioCadastro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_atualizacao_id")
    private Usuario usuarioAtualizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @CreatedDate
    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDate dataAtualizacao;

    public Cliente(String nomeRazao, String cpfCnpj, Empresa empresa, Usuario usuario) {
        this.nomeRazao = nomeRazao;
        this.cpfCnpj = cpfCnpj;
        this.empresa = empresa;
        this.usuarioCadastro = usuario;
        this.usuarioAtualizacao = usuario;
        this.fornecedor = true;
    }
}
