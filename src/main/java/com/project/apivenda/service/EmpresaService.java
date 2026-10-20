package com.project.apivenda.service;

import com.project.apivenda.dto.request.EmpresaRequest;
import com.project.apivenda.dto.response.EmpresaResponse;
import com.project.apivenda.mapper.EmpresaMapper;
import com.project.apivenda.repository.EmpresaRepository;
import com.project.apivenda.service.validator.EmpresaValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmpresaService {
    private final EmpresaRepository repository;
    private final EmpresaMapper mapper;
    private final EmpresaValidator validator;

    public EmpresaResponse insert(EmpresaRequest dto) {
        var entity = mapper.toEntity(dto);
        validator.validateEntity(entity, null);
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    public EmpresaResponse update(EmpresaRequest dto, Long id) {
        var newEntity = mapper.toEntity(dto);
        var currentEntity = validator.validateEntityById(id);

        validator.validateEntity(newEntity, id);
        validator.updateData(newEntity, currentEntity);

        return mapper.toDto(repository.save(currentEntity));
    }
}
