package com.project.apivenda.service.validator;

import com.project.apivenda.entity.Usuario;
import com.project.apivenda.exception.ErrorCode;
import com.project.apivenda.exception.exceptions.BusinessException;
import com.project.apivenda.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioValidator {
    private final UsuarioRepository repository;

    public Usuario validateEntityById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    public Usuario validateEntityByEmail(String email) {
        return repository.findByUsername(email).orElseThrow(() ->
                new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    public void validateEntity(Usuario entity, Long id) {
        if (existsEntity(entity, id)) {
            throw new BusinessException(ErrorCode.USER_INVALID);
        }

        if (existsEntityByUsername(entity, id)) {
            throw new BusinessException(ErrorCode.USER_EMAIL_EXISTING);
        }
    }

    public boolean existsEntity(Usuario entity, Long id) {
        Optional<Usuario> currentEntity = repository.findByEmailAndNomeAndCpf(
                entity.getEmail(),
                entity.getNome(),
                entity.getCpf()
        );

        if (id == null) {
            return currentEntity.isPresent();
        }

        return currentEntity.isPresent() && !id.equals(currentEntity.get().getId());
    }

    private boolean existsEntityByUsername(Usuario entity, Long id) {
        Optional<Usuario> currentEntity = repository.findByUsername(entity.getEmail());

        if (id == null) {
            return currentEntity.isPresent();
        }

        return currentEntity.isPresent() && !id.equals(currentEntity.get().getId());
    }

    public void updateData(Usuario currentEntity, Usuario newEntity) {
        currentEntity.setEmail(newEntity.getEmail());
        currentEntity.setNome(newEntity.getNome());
        currentEntity.setCpf(newEntity.getCpf());
        currentEntity.setAtivo(newEntity.getAtivo());
    }
}
