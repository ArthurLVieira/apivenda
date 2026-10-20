package com.project.apivenda.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemVendaRequest {
    private Long vendaId;
    private Long produtoId;
    private BigDecimal precoVenda;
    private BigDecimal quantidade;
}
