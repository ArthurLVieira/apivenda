package com.project.apivenda.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequest {
    private String email;
    private String senha;
    private String nome;
    private String sobrenome;
    private String cpf;
    private Boolean ativo = true;
}
