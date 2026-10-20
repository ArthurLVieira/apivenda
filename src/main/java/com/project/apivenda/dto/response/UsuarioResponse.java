package com.project.apivenda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {
    private Long id;
    private String email;
    private String senha;
    private String nome;
    private String sobrenome;
    private String cpf;
    private Boolean ativo = true;
    private Set<RoleResponse> roles;
    private EmpresaResponse empresa;
}
