package com.project.apivenda.service.validator;

import com.project.apivenda.entity.Role;
import com.project.apivenda.exception.ErrorCode;
import com.project.apivenda.exception.exceptions.BusinessException;
import com.project.apivenda.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleValidator {
    private final RoleRepository repository;

    public Role validateEntityByNome(String nome) {
        return repository.findByName(nome).orElseThrow(() ->
                new BusinessException(ErrorCode.ROLE_NOT_FOUND));
    }
}
