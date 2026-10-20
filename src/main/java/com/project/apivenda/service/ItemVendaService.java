package com.project.apivenda.service;

import com.project.apivenda.dto.request.ItemVendaRequest;
import com.project.apivenda.dto.response.ItemVendaResponse;
import com.project.apivenda.entity.ItemVenda;
import com.project.apivenda.entity.Produto;
import com.project.apivenda.entity.Venda;
import com.project.apivenda.exception.ErrorCode;
import com.project.apivenda.exception.exceptions.BusinessException;
import com.project.apivenda.mapper.ItemVendaMapper;
import com.project.apivenda.repository.ItemVendaRepository;
import com.project.apivenda.service.validator.ItemVendaValidator;
import com.project.apivenda.service.validator.ProdutoValidator;
import com.project.apivenda.service.validator.VendaValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ItemVendaService {
    private final ItemVendaRepository itemVendaRepository;
    private final ItemVendaMapper itemVendaMapper;
    private final ItemVendaValidator itemVendaValidator;
    private final VendaValidator vendaValidator;
    private final ProdutoValidator produtoValidator;

    public Page<ItemVendaResponse> findByVenda(
            Long vendaId,  Pageable pageable
    ) {
        Page<ItemVenda> itensVenda =  itemVendaRepository.findByVendaId(vendaId, pageable);
        return itensVenda.map(itemVendaMapper::toDto);
    }

    public ItemVendaResponse insert(ItemVendaRequest request) {
        Venda venda = vendaValidator.existsEntityById(request.getVendaId());

        Produto produto = produtoValidator.existsEntityById(request.getProdutoId());

        ItemVenda entity = itemVendaMapper.toEntity(request);
        itemVendaValidator.validateItemVenda(entity);

        entity.setVenda(venda);
        entity.setPrecoVenda(request.getPrecoVenda() != null ? request.getPrecoVenda() : produto.getPreco());
        entity.setQuantidade(request.getQuantidade() != null ? request.getQuantidade() : BigDecimal.ONE);

        return itemVendaMapper.toDto(itemVendaRepository.save(entity));
    }

    public ItemVendaResponse update(ItemVendaRequest request, Long id) {
        ItemVenda aux = itemVendaRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.ITEM_VENDA_NOT_FOUND));
        ItemVenda entity = itemVendaMapper.toEntity(request);

        itemVendaValidator.validateItemVenda(entity);
        itemVendaValidator.updateItemVenda(entity, aux);

        return itemVendaMapper.toDto(itemVendaRepository.save(entity));
    }

    public void delete(Long id) {
        itemVendaRepository.deleteById(id);
    }
}