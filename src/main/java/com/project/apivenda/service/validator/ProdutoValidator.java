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
        Optional<Produto> aux = repository.findByDescricaoAndCodigoDeBarrasAndEmpresa(
                entity.getDescricao(),
                entity.getCodigoDeBarras(),
                entity.getEmpresa()
        );

        if (id == null) {
            return aux.isPresent();
        }

        return aux.isPresent() && !id.equals(aux.get().getId());
    }

    private boolean existsEntityByCodigoDeBarras(Produto entity, Long id) {
        Optional<Produto> aux = repository.findByCodigoDeBarrasAndEmpresa(
                entity.getCodigoDeBarras(),
                entity.getEmpresa()
        );

        if (id == null) {
            return aux.isPresent();
        }

        return aux.isPresent() && !id.equals(aux.get().getId());
    }

    public void updateData(Produto currentEntity, Produto newEntity) {
        currentEntity.setDescricao(newEntity.getDescricao());
        currentEntity.setCodigoDeBarras(newEntity.getCodigoDeBarras());
        currentEntity.setPreco(newEntity.getPreco());
        currentEntity.setQuantidade(newEntity.getQuantidade());
        currentEntity.setUsuarioAtualizacao(newEntity.getUsuarioAtualizacao());
    }
}
