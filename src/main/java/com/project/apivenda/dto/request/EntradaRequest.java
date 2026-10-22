package com.project.apivenda.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntradaRequest {
    private String numero;
    private Long fornecedorId;
    private String nomeRazao;
    private String cpfCnpj;
    private List<ItemEntradaRequest> itemEntradas;
    private BigDecimal total;

}
