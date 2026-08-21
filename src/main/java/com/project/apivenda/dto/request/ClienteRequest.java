package com.project.apivenda.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRequest {
    @NotNull
    private String nomeCompleto;
    private String cpf;
    private LocalDate dataNascimento;
}
