package com.project.apivenda.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRequest {
    @NotNull(message = "Nome/Razão do cliente obrigatório")
    private String nomeRazao;
    @NotNull(message = "CPF/CNPJ do cliente obrigatório")
    private String cpfCnpj;
    private String nomeFantazia;
    private LocalDate dataNascimentoCriacao;
    private Boolean fornecedor = false;
    private boolean status = true;
}
