package com.project.apivenda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {
    private Long id;
    private String nomeRazao;
    private String cpfCnpj;
    private String nomeFantazia;
    private LocalDate dataNascimentoCriacao;
    private Boolean fornecedor;
    private boolean status;
}
