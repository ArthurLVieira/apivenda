package com.project.apivenda.mapper;

import com.project.apivenda.dto.request.EntradaRequest;
import com.project.apivenda.dto.response.EntradaResponse;
import com.project.apivenda.entity.Entrada;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {ItemEntradaMapper.class, ClienteMapper.class})
public interface EntradaMapper {
    Entrada toEntity(EntradaRequest dto);
    EntradaResponse toDto(Entrada entity);

    @AfterMapping
    default void afterMapping(Entrada entity, @MappingTarget EntradaResponse dto) {
        entity.getItens().forEach(item -> {
            item.setId(entity.getId());
        });

        dto.setFornecedorId(entity.getFornecedor().getId());
        dto.setNomeRazao(entity.getNomeRazao());
        dto.setCpfCnpj(entity.getCpfCnpj());
    }
}
