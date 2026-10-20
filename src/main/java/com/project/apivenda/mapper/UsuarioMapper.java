package com.project.apivenda.mapper;

import com.project.apivenda.dto.request.UsuarioRequest;
import com.project.apivenda.dto.response.UsuarioResponse;
import com.project.apivenda.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UsuarioMapper {
    @Mappings({
            @Mapping(target = "roles", ignore = true),
            @Mapping(target = "empresa", ignore = true),
            @Mapping(target = "id", ignore = true),
    })
    Usuario toEntity(UsuarioRequest dto);

    @Mappings({
            @Mapping(target = "roles", source = "roles"),
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "empresa", source = "empresa")
    })
    UsuarioResponse toDto(Usuario entity);
}
