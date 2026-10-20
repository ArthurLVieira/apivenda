package com.project.apivenda.dto.response;

import com.project.apivenda.entity.enums.StatusVendaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendaResponse {
    private ClienteResponse cliente;
    private List<ItemVendaResponse> itens;
    private BigDecimal total;
    private StatusVendaEnum status;
}
