package com.project.apivenda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntradaResponse {
    private Long id;
    private Long entradaId;
    private Long produtoId;
    private String descricao;
    private String codigoDeBarras;
    private BigDecimal preco;
    private BigDecimal quantidade;
    private BigDecimal total;
}
