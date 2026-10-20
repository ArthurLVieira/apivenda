package com.project.apivenda.mapper;

import com.project.apivenda.dto.response.RoleResponse;
import com.project.apivenda.entity.Role;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleResponse toDto(Role role);

    Set<RoleResponse> listToDto(Set<Role> roles);
}
