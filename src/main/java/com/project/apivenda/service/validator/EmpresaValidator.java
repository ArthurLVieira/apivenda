package com.project.apivenda.service.validator;

import com.project.apivenda.entity.Empresa;
import com.project.apivenda.exception.ErrorCode;
import com.project.apivenda.exception.exceptions.BusinessException;
import com.project.apivenda.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EmpresaValidator {
    private final EmpresaRepository repository;

    public Empresa validateEntityById(Long id) {
        return  repository.findById(id).orElseThrow(() ->
            new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    public void validateEntity(Empresa entity, Long id) {
        if (existsEntity(entity, id)) {
            throw new BusinessException(ErrorCode.ENTERPRISE_INVALID);
        }

        if (existsEntityByCpfCnpj(entity, id)) {
            throw new BusinessException(ErrorCode.ENTERPRISE_CPFCNPJ_EXISTING);
        }
    }

    private boolean existsEntityByCpfCnpj(Empresa entity, Long id) {
        Optional<Empresa> currentEntity = repository.findByCpfCnpj(entity.getCpfCnpj());

        if (id == null) {
            return currentEntity.isPresent();
        }

        return currentEntity.isPresent() && id.equals(currentEntity.get().getId());
    }

    private boolean existsEntity(Empresa entity, Long id) {
        Optional<Empresa> currentEntity = repository.findByNomeRazaoAndCpfCnpjAndEmail(
                entity.getNomeRazao(), entity.getCpfCnpj(), entity.getEmail()
        );

        if (id == null) {
            return currentEntity.isPresent();
        }

        return currentEntity.isPresent() && !currentEntity.get().getId().equals(id);
    }

    public void updateData(Empresa newEntity, Empresa currentEntity) {
        currentEntity.setNomeRazao(newEntity.getNomeRazao());
        currentEntity.setCpfCnpj(newEntity.getCpfCnpj());
        currentEntity.setLogoUrl(newEntity.getLogoUrl());
        currentEntity.setEmail(newEntity.getEmail());
    }
}
