package com.project.apivenda.service.validator;

import com.project.apivenda.entity.Cliente;
import com.project.apivenda.exception.ErrorCode;
import com.project.apivenda.exception.exceptions.BusinessException;
import com.project.apivenda.repository.ClienteRepository;
import com.project.apivenda.repository.VendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClienteValidator {
    private final ClienteRepository repository;
    private final VendaRepository vendaRepository;

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
        Optional<Cliente> aux = repository.findByCpfAndEmpresa(entity.getCpf(), entity.getEmpresa());

        if (id == null) {
            return aux.isPresent();
        }

        return !id.equals(aux.get().getId()) && aux.isPresent();
    }

    private Boolean existsByNomeCompletoAndCpf(Cliente entity, Long id) {
        Optional<Cliente> aux = repository.findByNomeCompletoAndCpfAndEmpresa(
                entity.getNomeCompleto(),
                entity.getCpf(),
                entity.getEmpresa());

        if (id == null) {
            return aux.isPresent();
        }

        return !id.equals(aux.get().getId()) && aux.isPresent();
    }

    public void updateData(Cliente newEntity, Cliente currentEntity) {
        currentEntity.setNomeCompleto(newEntity.getNomeCompleto());
        currentEntity.setCpf(newEntity.getCpf());
        currentEntity.setDataNascimento(newEntity.getDataNascimento());
        currentEntity.setUsuarioAtualizacao(newEntity.getUsuarioAtualizacao());

    }

    public void validateCanDelete(Cliente cliente) {
    }
}
