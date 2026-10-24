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
        if (existsByNomeRazaoAndCpfCnpj(entity, id)) {
            throw new BusinessException(ErrorCode.CLIENT_HAS_EXISTS);
        }
        if (existsByCpfCnpj(entity, id)) {
            throw new BusinessException(ErrorCode.CLIENT_CPF_HAS_EXISTS);
        }
    }

    private boolean existsByCpfCnpj(Cliente entity, Long id) {
        Optional<Cliente> aux = repository.findByCpfCnpjAndEmpresa(entity.getCpfCnpj(), entity.getEmpresa());

        if (id == null) {
            return aux.isPresent();
        }

        return !id.equals(aux.get().getId()) && aux.isPresent();
    }

    private Boolean existsByNomeRazaoAndCpfCnpj(Cliente entity, Long id) {
        Optional<Cliente> aux = repository.findByNomeRazaoAndCpfCnpjAndEmpresa(
                entity.getNomeRazao(),
                entity.getCpfCnpj(),
                entity.getEmpresa());

        if (id == null) {
            return aux.isPresent();
        }

        return !id.equals(aux.get().getId()) && aux.isPresent();
    }

    public void updateData(Cliente newEntity, Cliente currentEntity) {
        currentEntity.setNomeRazao(newEntity.getNomeRazao());
        currentEntity.setCpfCnpj(newEntity.getCpfCnpj());
        currentEntity.setDataNascimentoCriacao(newEntity.getDataNascimentoCriacao());
        currentEntity.setUsuarioAtualizacao(newEntity.getUsuarioAtualizacao());
        currentEntity.setFornecedor(newEntity.getFornecedor());
    }

    public void validateCanDelete(Cliente cliente) {
    }
}
