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
public class ItemVendaResponse {
    private VendaResponse venda;
    private ProdutoResponse produto;
    private BigDecimal precoVenda;
    private BigDecimal quantidade;
    private BigDecimal subtotal;
}
