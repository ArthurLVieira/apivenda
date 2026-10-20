package com.project.apivenda.mapper;

import com.project.apivenda.dto.request.ItemVendaRequest;
import com.project.apivenda.dto.response.ItemVendaResponse;
import com.project.apivenda.entity.ItemVenda;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses =  {ProdutoMapper.class})
public interface ItemVendaMapper {
    @Mappings({
            @Mapping(target = "venda", ignore = true),
            @Mapping(target = "produto.id", source = "produtoId")
    })
    ItemVenda toEntity(ItemVendaRequest request);

    @Mappings({
            @Mapping(target = "venda",  ignore = true),
            @Mapping(target = "produto", source = "produto")
    })
    ItemVendaResponse toDto(ItemVenda entity);

    List<ItemVenda> toEntityList(List<ItemVendaRequest> dto);

    List<ItemVendaResponse> toDtoList(List<ItemVenda> entity);
}
