package com.project.apivenda.service.validator;

import com.project.apivenda.repository.StatusEntradaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class StatusEntradaValidator {

    private final StatusEntradaRepository statusEntradaRepository;
}
