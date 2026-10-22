package com.project.apivenda.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemEntradaRequest {
    private Long entradaId;
    private Long produtoId;
    private String descricao;
    private String codigoDeBarras;
    private BigDecimal preco;
    private BigDecimal quantidade;
    private BigDecimal total;
}
