package com.project.apivenda.mapper;

import com.project.apivenda.dto.request.ProdutoRequest;
import com.project.apivenda.dto.response.ProdutoResponse;
import com.project.apivenda.entity.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    @Mapping(target = "itensVenda", ignore = true)
    Produto toEntity(ProdutoRequest request);

    ProdutoResponse toDto(Produto entity);
}
