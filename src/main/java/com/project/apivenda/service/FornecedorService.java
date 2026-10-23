package com.project.apivenda.service;

import com.project.apivenda.entity.Cliente;
import com.project.apivenda.entity.Empresa;
import com.project.apivenda.entity.Usuario;
import com.project.apivenda.exception.ErrorCode;
import com.project.apivenda.exception.exceptions.BusinessException;
import com.project.apivenda.repository.ClienteRepository;
import com.project.apivenda.repository.UsuarioRepository;
import com.project.apivenda.service.validator.ClienteValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FornecedorService {

    private final ClienteRepository clienteRepository;
    private final ClienteValidator clienteValidator;
    private UsuarioRepository usuarioRepository;

    private Usuario getUsuario() {
        String email = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        return usuarioRepository.findByUsername(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    private Empresa getEmpresa() {
        return getUsuario().getEmpresa();
    }

    public Cliente insertOrUpdate(Long id, String nomeRazao, String cpfCnpj) {
        Optional<Cliente> entity =  clienteRepository.findById(id);

        if (entity.isEmpty()) {
            entity = clienteRepository.findByCpfCnpjAndEmpresa(cpfCnpj, getEmpresa());

            if (entity.isEmpty()) {
                return clienteRepository.save(new Cliente(nomeRazao, cpfCnpj, getEmpresa(), getUsuario()));
            }

            var newEntity = new Cliente(nomeRazao, cpfCnpj, getEmpresa(), getUsuario());
            var currentEntity = entity.get();

            clienteValidator.validateEntity(newEntity, id);
            clienteValidator.updateData(newEntity, currentEntity);

            return clienteRepository.save(currentEntity);
        }

        var newEntity = new Cliente(nomeRazao, cpfCnpj, getEmpresa(), getUsuario());
        var currentEntity = entity.get();

        clienteValidator.validateEntity(newEntity, id);
        clienteValidator.updateData(newEntity, currentEntity);

        return clienteRepository.save(currentEntity);
    }
}
