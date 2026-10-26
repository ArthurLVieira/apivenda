package com.project.apivenda.service.validator;

import com.project.apivenda.entity.StatusVenda;
import com.project.apivenda.entity.enums.StatusVendaEnum;
import com.project.apivenda.exception.ErrorCode;
import com.project.apivenda.exception.exceptions.BusinessException;
import com.project.apivenda.repository.StatusVendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StatusVendaValidator {
    private final StatusVendaRepository statusVendaRepository;

    public void validateInitializerEntityByStatus() {
        for (StatusVendaEnum statusVenda : StatusVendaEnum.values()) {
            Optional<StatusVenda> entity = statusVendaRepository.findByStatus(statusVenda);

            if (entity.isEmpty()) {
                statusVendaRepository.save(new StatusVenda(statusVenda));
            }
        }


    }

    public StatusVenda validateEntityByStatus(StatusVendaEnum status) {
        return statusVendaRepository.findByStatus(status)
                .orElseThrow(() ->
                        new BusinessException(ErrorCode.STATUS_VENDA_ERROR));
    }
}
