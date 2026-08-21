package com.project.apivenda.service;

import com.project.apivenda.dto.request.ClienteRequest;
import com.project.apivenda.dto.response.ClienteResponse;
import com.project.apivenda.entity.Cliente;
import com.project.apivenda.exception.ErrorCode;
import com.project.apivenda.exception.exceptions.BusinessException;
import com.project.apivenda.mapper.ClienteMapper;
import com.project.apivenda.repository.ClienteRepository;
import com.project.apivenda.service.validator.ClienteValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository repository;
    private final ClienteMapper mapper;
    private final ClienteValidator validator;

    public ClienteResponse findById(Long id) {
        return mapper.toDto(validator.validateEntityId(id));
    }

    public Page<ClienteResponse> search(
            Pageable pageable,
            String search
    ) {
        Page<Cliente> clientes;
        try {
            if (search != null && !search.trim().isEmpty()) {
                clientes = repository.search(
                        search.trim(),
                        pageable
                );
            } else {
                clientes = repository.findAll(pageable);
            }
        }catch (Exception e) {
            throw new BusinessException(ErrorCode.INTERNAL_ERROR);
        }

        return clientes.map(mapper::toDto);
    }

    public ClienteResponse insert(ClienteRequest request) {
        Cliente entity = mapper.toEntity(request);
        validator.validateEntity(entity, null);
        return mapper.toDto(repository.save(entity));
    }

    public ClienteResponse update(ClienteRequest request, Long id) {
        Cliente newEntity = mapper.toEntity(request);
        validator.validateEntity(newEntity, null);
        Cliente entity = validator.validateEntityId(id);
        validator.updateData(newEntity, entity);
        return mapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        validator.validateEntityId(id);
        repository.deleteById(id);
    }

}
