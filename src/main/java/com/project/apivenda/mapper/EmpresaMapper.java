package com.project.apivenda.mapper;

import com.project.apivenda.dto.request.EmpresaRequest;
import com.project.apivenda.dto.response.EmpresaResponse;
import com.project.apivenda.entity.Empresa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmpresaMapper {
    @Mapping(target = "id", ignore = true)
    Empresa toEntity(EmpresaRequest dto);
    EmpresaResponse toDto(Empresa entity);
}
