package com.project.apivenda.service;

import com.project.apivenda.dto.request.UsuarioRequest;
import com.project.apivenda.dto.response.AuthResponse;
import com.project.apivenda.dto.response.UsuarioResponse;
import com.project.apivenda.entity.Empresa;
import com.project.apivenda.entity.Role;
import com.project.apivenda.entity.Usuario;
import com.project.apivenda.exception.ErrorCode;
import com.project.apivenda.exception.exceptions.BusinessException;
import com.project.apivenda.mapper.UsuarioMapper;
import com.project.apivenda.repository.UsuarioRepository;
import com.project.apivenda.service.validator.RoleValidator;
import com.project.apivenda.service.validator.UsuarioValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;
    private final UsuarioValidator validator;
    private final RoleValidator roleValidator;

    private Usuario getUsuario() {
        String email = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        return repository.findByUsername(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    private Empresa getEmpresa() {
        return getUsuario().getEmpresa();
    }

    public UsuarioResponse findById(Long id) {
        return mapper.toDto(validator.validateEntityById(id));
    }

    public UsuarioResponse insert(UsuarioRequest dto) {
        var entity = mapper.toEntity(dto);

        Set<Role>  roles = new HashSet<>();
        roles.add(roleValidator.validateEntityByNome("ADMIN"));
        roles.add(roleValidator.validateEntityByNome("USER"));

        entity.setEmpresa(getEmpresa());
        entity.setRoles(roles);

        validator.validateEntity(entity, null);
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    public UsuarioResponse update(UsuarioRequest dto, Long id) {
        var newEntity = mapper.toEntity(dto);
        var currentEntity = validator.validateEntityById(id);

        validator.validateEntity(newEntity, id);
        validator.updateData(currentEntity, newEntity);

        return mapper.toDto(repository.save(currentEntity));
    }

    public UsuarioResponse updateLogin(Usuario entity) {
        return mapper.toDto(repository.save(entity));
    }
}
