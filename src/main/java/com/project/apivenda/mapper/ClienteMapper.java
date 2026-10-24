package com.project.apivenda.mapper;

import com.project.apivenda.dto.request.ClienteRequest;
import com.project.apivenda.dto.response.ClienteResponse;
import com.project.apivenda.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    @Mappings({
            @Mapping(target = "vendas", ignore = true),
            @Mapping(target = "entradas", ignore = true),
            @Mapping(target = "dataAtualizacao", ignore = true),
            @Mapping(target = "dataCadastro", ignore = true),
            @Mapping(target = "empresa", ignore = true),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "usuarioAtualizacao", ignore = true),
            @Mapping(target = "usuarioCadastro", ignore = true),
    })
    Cliente toEntity(ClienteRequest request);

    @Mappings({
            @Mapping(target = "id", source = "id")
    })
    ClienteResponse toDto(Cliente entity);
}
