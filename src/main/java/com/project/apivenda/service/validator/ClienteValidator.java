package com.project.apivenda.service.validator;

import com.project.apivenda.entity.Cliente;
import com.project.apivenda.exception.ErrorCode;
import com.project.apivenda.exception.exceptions.BusinessException;
import com.project.apivenda.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClienteValidator {
    private final ClienteRepository repository;

    public Cliente validateEntityId(Long id) {
        return repository.findById(id).orElseThrow(() ->
            new BusinessException(ErrorCode.CLIENT_NOT_FOUND));
    }

    public void validateEntity(Cliente entity, Long id) {
        if (existsByNomeCompletoAndCpf(entity, id)) {
            throw new BusinessException(ErrorCode.CLIENT_HAS_EXISTS);
        }
        if (existsByCpf(entity, id)) {
            throw new BusinessException(ErrorCode.CLIENT_CPF_HAS_EXISTS);
        }
    }

    private boolean existsByCpf(Cliente entity, Long id) {
        Optional<Cliente> aux = repository.findByCpf(entity.getCpf());

        if (id == null) {
            return aux.isPresent();
        }

        return !id.equals(aux.get().getId()) && aux.isPresent();
    }

    private Boolean existsByNomeCompletoAndCpf(Cliente entity, Long id) {
        Optional<Cliente> aux = repository.findByNomeCompletoAndCpf(
                entity.getNomeCompleto(),
                entity.getCpf());

        if (id == null) {
            return aux.isPresent();
        }

        return !id.equals(aux.get().getId()) && aux.isPresent();
    }

    public void updateData(Cliente newEntity, Cliente entity) {
        entity.setNomeCompleto(newEntity.getNomeCompleto());
        entity.setCpf(newEntity.getCpf());
        entity.setDataNascimento(newEntity.getDataNascimento());
    }
}
