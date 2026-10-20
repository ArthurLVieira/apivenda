package com.project.apivenda.service;

import com.project.apivenda.dto.request.ProdutoRequest;
import com.project.apivenda.dto.response.ProdutoResponse;
import com.project.apivenda.entity.Cliente;
import com.project.apivenda.entity.Produto;
import com.project.apivenda.exception.ErrorCode;
import com.project.apivenda.exception.exceptions.BusinessException;
import com.project.apivenda.mapper.ProdutoMapper;
import com.project.apivenda.repository.ProdutoRepository;
import com.project.apivenda.service.validator.ProdutoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository repository;
    private final ProdutoValidator validator;
    private final ProdutoMapper mapper;

    public ProdutoResponse findById(Long id) {
        return mapper.toDto(validator.existsEntityById(id));
    }

    public Page<ProdutoResponse> search(String search, Pageable pageable) {
        Page<Produto> produtos;
        try {
            if (search != null && !search.trim().isEmpty()) {
                produtos = repository.search(
                        search.trim(),
                        pageable
                );
            } else {
                produtos = repository.findAll(pageable);
            }
        }catch (Exception e) {
            throw new BusinessException(ErrorCode.INTERNAL_ERROR);
        }

        return produtos.map(mapper::toDto);
    }

    public ProdutoResponse insert(ProdutoRequest request) {
        Produto entity = mapper.toEntity(request);
        validator.validateEntity(entity, null);
        return mapper.toDto(repository.save(entity));
    }

    public ProdutoResponse update(ProdutoRequest request, Long id) {
        Produto entity = validator.existsEntityById(id);
        Produto newEntity = mapper.toEntity(request);
        validator.validateEntity(entity, null);
        validator.updateData(entity, newEntity);
        return mapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        validator.existsEntityById(id);
        repository.deleteById(id);
    }
}
