package com.project.apivenda.dto.request;

import com.project.apivenda.entity.enums.StatusVendaEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendaRequest {
    private Long clienteId;
    private StatusVendaEnum status;
    private List<ItemVendaRequest> items;
}
