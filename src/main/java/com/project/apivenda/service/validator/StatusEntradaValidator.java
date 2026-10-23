package com.project.apivenda.service.validator;

import com.project.apivenda.entity.StatusEntrada;
import com.project.apivenda.entity.enums.StatusEntradaEnum;
import com.project.apivenda.exception.ErrorCode;
import com.project.apivenda.exception.exceptions.BusinessException;
import com.project.apivenda.repository.StatusEntradaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class StatusEntradaValidator {

    private final StatusEntradaRepository statusEntradaRepository;

    public StatusEntrada validateInitializerEntityByStatus(StatusEntradaEnum status) {
        return  statusEntradaRepository.findByStatus(status)
                .orElse(statusEntradaRepository.save(new StatusEntrada(status)));
    }

    public StatusEntrada validateEntityByStatus(StatusEntradaEnum status) {
        return statusEntradaRepository.findByStatus(status)
                .orElseThrow(() ->
                        new BusinessException(ErrorCode.STATUS_ENTRADA_ERROR));
    }
}
