package com.project.apivenda.config;

import com.project.apivenda.entity.Role;
import com.project.apivenda.entity.enums.StatusVendaEnum;
import com.project.apivenda.repository.RoleRepository;
import com.project.apivenda.repository.StatusVendaRepository;
import com.project.apivenda.service.validator.StatusVendaValidator;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(value = 1)
public class InitializerConfig implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final StatusVendaRepository statusVendaRepository;
    private final StatusVendaValidator statusVendaValidator;

    @Override
    public void run(String @NonNull ... args) throws Exception {
        initializeRoles();
        initializeStatusVenda();
    }

    private void initializeRoles() {
        if  (roleRepository.count() != 0) {return;}

        roleRepository.save(new Role("ADMIN"));
        roleRepository.save(new Role("USER"));
    }

    private void initializeStatusVenda() {
        if  (statusVendaRepository.count() != 0) {return;}

        statusVendaRepository.save(statusVendaValidator.validateInitializerEntityByStatus(StatusVendaEnum.ABERTA));
        statusVendaRepository.save(statusVendaValidator.validateInitializerEntityByStatus(StatusVendaEnum.FATURADA));
        statusVendaRepository.save(statusVendaValidator.validateInitializerEntityByStatus(StatusVendaEnum.CANCELADA));
    }
}
