package com.project.apivenda.mapper;

import com.project.apivenda.dto.request.VendaRequest;
import com.project.apivenda.dto.response.VendaResponse;
import com.project.apivenda.entity.Venda;
import com.project.apivenda.service.validator.StatusVendaValidator;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {ItemVendaMapper.class, ClienteMapper.class})
public abstract class VendaMapper {

    @Autowired
    protected StatusVendaValidator statusVendaValidator;

    @Mappings({
            @Mapping(target = "cliente.id", source = "clienteId"),
            @Mapping(target = "status", expression = ("java(statusVendaValidator.validateEntityByStatus(request.getStatus()))"))
    })
    public abstract Venda toEntity(VendaRequest request);

    @Mappings({
            @Mapping(target = "status", source = "entity.status.status")
    })
    public abstract VendaResponse toDto(Venda entity);

    @AfterMapping
    protected void vincular(@MappingTarget Venda venda) {
        if (venda.getItens() != null) {
            venda.getItens().forEach(itemVenda -> itemVenda.setVenda(venda));
        }
    }
}
