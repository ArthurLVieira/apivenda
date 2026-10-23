package com.project.apivenda.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoRequest {
    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    private String codigoDeBarras;

    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.00", message = "Preço não pode ser negativo")
    private BigDecimal preco;

    @NotNull(message = "Quantidade é obrigatória")
    @DecimalMin(value = "0.000", message = "Quantidade não pode ser negativa")
    private BigDecimal quantidade;
}
