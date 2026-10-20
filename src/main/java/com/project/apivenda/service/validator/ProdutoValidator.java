package com.project.apivenda.service.validator;

import com.project.apivenda.entity.Produto;
import com.project.apivenda.exception.ErrorCode;
import com.project.apivenda.exception.exceptions.BusinessException;
import com.project.apivenda.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProdutoValidator {
    private final ProdutoRepository repository;

    public Produto existsEntityById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    public void validateEntity(Produto entity, Long id) {
        if (existsEntityByDescricaoAndCodigoBarra(entity, id)) {
            throw new BusinessException(ErrorCode.PRODUCT_HAS_EXISTS);
        }

        if (existsEntityByCodigoDeBarras(entity, id)) {
            throw new BusinessException(ErrorCode.PRODUCT_COGIDODEBARRAS_HAS_EXISTS);
        }
    }

    private boolean existsEntityByDescricaoAndCodigoBarra(Produto entity, Long id) {
        Optional<Produto> aux = repository.findByDescricaoAndCodigoDeBarras(entity.getDescricao(), entity.getCodigoDeBarras());

        if (id == null) {
            return aux.isPresent();
        }

        return aux.isPresent() && !id.equals(aux.get().getId());
    }

    private boolean existsEntityByCodigoDeBarras(Produto entity, Long id) {
        Optional<Produto> aux = repository.findByCodigoDeBarras(entity.getCodigoDeBarras());

        if (id == null) {
            return aux.isPresent();
        }

        return aux.isPresent() && !id.equals(aux.get().getId());
    }

    public void updateData(Produto entity, Produto newEntity) {
        entity.setDescricao(newEntity.getDescricao());
        entity.setCodigoDeBarras(newEntity.getCodigoDeBarras());
        entity.setPreco(newEntity.getPreco());
        entity.setQuantidade(newEntity.getQuantidade());
    }
}
