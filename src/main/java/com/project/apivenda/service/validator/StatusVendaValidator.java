package com.project.apivenda.service.validator;

import com.project.apivenda.entity.StatusVenda;
import com.project.apivenda.entity.enums.StatusVendaEnum;
import com.project.apivenda.exception.ErrorCode;
import com.project.apivenda.exception.exceptions.BusinessException;
import com.project.apivenda.repository.StatusVendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatusVendaValidator {
    private final StatusVendaRepository statusVendaRepository;

    public StatusVenda validateInitializerEntityByStatus(StatusVendaEnum status) {
        return statusVendaRepository.findByStatus(status)
                .orElse(statusVendaRepository.save(new StatusVenda(status)));
    }

    public StatusVenda validateEntityByStatus(StatusVendaEnum status) {
        return statusVendaRepository.findByStatus(status)
                .orElseThrow(() ->
                        new BusinessException(ErrorCode.STATUS_VENDA_ERROR));
    }
}
