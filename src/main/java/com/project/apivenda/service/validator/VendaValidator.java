package com.project.apivenda.service.validator;

import com.project.apivenda.entity.Venda;
import com.project.apivenda.exception.ErrorCode;
import com.project.apivenda.exception.exceptions.BusinessException;
import com.project.apivenda.repository.ClienteRepository;
import com.project.apivenda.repository.VendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VendaValidator {
    private final VendaRepository vendaRepository;
    private final ClienteRepository clienteRepository;
    private final ItemVendaValidator itemVendaValidate;
    private final ClienteValidator clienteValidator;

    public Venda existsEntityById(Long id) {
        return vendaRepository.findById(id).orElseThrow(() ->
                new BusinessException(ErrorCode.VENDA_NOT_FOUND));
    }

    public void validateEntity(Venda entity, Long id) {
        entity.getItens().forEach(itemVendaValidate::validateItemVenda);
        clienteValidator.validateEntityId(entity.getCliente().getId());
    }

}
