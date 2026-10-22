package com.project.apivenda.service.validator;

import com.project.apivenda.entity.Entrada;
import com.project.apivenda.exception.ErrorCode;
import com.project.apivenda.exception.exceptions.BusinessException;
import com.project.apivenda.repository.EntradaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EntradaValidator {

    private final EntradaRepository entradaRepository;

    public Entrada validateById(Long id) {
        return entradaRepository.findById(id).orElseThrow(() ->
                new BusinessException(ErrorCode.ENTRADA_NOT_FOUND));
    }
}
