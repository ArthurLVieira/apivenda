package com.project.apivenda.mapper;

import com.project.apivenda.dto.request.ClienteRequest;
import com.project.apivenda.dto.response.ClienteResponse;
import com.project.apivenda.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    @Mapping(target = "vendas", ignore = true)
    Cliente toEntity(ClienteRequest request);

    ClienteResponse toDto(Cliente entity);
}
