package com.project.apivenda.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRequest {
    @NotNull(message = "Nome do cliente obrigatório")
    private String nomeCompleto;
    @NotNull(message = "CPF do cliente obrigatório")
    private String cpf;
    private LocalDate dataNascimento;
}
