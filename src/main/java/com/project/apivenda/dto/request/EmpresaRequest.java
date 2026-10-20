package com.project.apivenda.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaRequest {
    private String nomeRazao;
    private String cpfCnpj;
    private String logoUrl;
    private String email;
}
