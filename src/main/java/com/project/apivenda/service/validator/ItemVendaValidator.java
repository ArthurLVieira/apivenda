package com.project.apivenda.service.validator;

import com.project.apivenda.entity.ItemVenda;
import com.project.apivenda.entity.Produto;
import com.project.apivenda.entity.Venda;
import com.project.apivenda.exception.ErrorCode;
import com.project.apivenda.exception.exceptions.BusinessException;
import com.project.apivenda.repository.ItemVendaRepository;
import com.project.apivenda.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ItemVendaValidator {
    private final ItemVendaRepository itemVendaRepository;
    private final ProdutoRepository produtoRepository;

    public void validateItemVenda(ItemVenda entity) {
        Produto produto = produtoRepository.findById(entity.getProduto().getId()).orElseThrow(() ->
                new BusinessException(ErrorCode.CLIENT_NOT_FOUND));

        if (produto.getQuantidade().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(ErrorCode.PRODUCT_QTD_INVALID);
        }

        if (entity.getQuantidade().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(ErrorCode.ITEM_VENDA_QTD_INVALID);
        }

        if (produto.getQuantidade().compareTo(entity.getQuantidade()) < 0) {
            throw new BusinessException(ErrorCode.PRODUCT_QTD_INVALID);
        }

        if (entity.getPrecoVenda().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(ErrorCode.ITEM_VENDA_PRECO_VENDA_INVALID);
        }
    }

    public void validateItens(Venda newEntity, Venda entity) {
        newEntity.getItens().forEach(this::validateItemVenda);

        for (ItemVenda itemVenda : newEntity.getItens()) {
            entity.getItens().removeIf(it -> !itemVenda.getId().equals(it.getId()));
        }
    }

    public void updateItemVenda(ItemVenda entity,  ItemVenda newEntity) {
        validateItemVenda(newEntity);
        entity.setQuantidade(newEntity.getQuantidade());
        entity.setPrecoVenda(newEntity.getPrecoVenda());
        entity.setSubtotal(entity.getQuantidade().multiply(newEntity.getPrecoVenda()));
    };
}
