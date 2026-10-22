package com.project.apivenda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntradaResponse {
    private Long id;
    private String numero;
    private Long fornecedorId;
    private String nomeRazao;
    private String cpfCnpj;
    private List<ItemEntradaResponse> itemEntradas;
    private BigDecimal total;
    private LocalDate data;
}
