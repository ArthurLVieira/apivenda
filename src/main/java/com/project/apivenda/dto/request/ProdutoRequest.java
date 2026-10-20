package com.project.apivenda.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoRequest {
    private String descricao;
    private String codigoDeBarras;
    private BigDecimal valor;
    private BigDecimal quantidade;
}
