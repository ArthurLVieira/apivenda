package com.project.apivenda.mapper;

import com.project.apivenda.dto.request.ItemEntradaRequest;
import com.project.apivenda.dto.response.ItemEntradaResponse;
import com.project.apivenda.entity.ItemEntrada;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemEntradaMapper {
    ItemEntrada toEntity(ItemEntradaRequest dto);
    ItemEntradaResponse toDto(ItemEntrada entity);

    List<ItemEntrada> toEntities(List<ItemEntradaRequest> dtos);
    List<ItemEntradaResponse> toDtos(List<ItemEntrada> entities);
}
