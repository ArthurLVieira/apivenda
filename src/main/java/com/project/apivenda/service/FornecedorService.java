package com.project.apivenda.service;

import com.project.apivenda.entity.Cliente;
import com.project.apivenda.entity.Empresa;
import com.project.apivenda.entity.Usuario;
import com.project.apivenda.exception.ErrorCode;
import com.project.apivenda.exception.exceptions.BusinessException;
import com.project.apivenda.repository.ClienteRepository;
import com.project.apivenda.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FornecedorService {

    private final ClienteRepository clienteRepository;
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
        Optional<Cliente> currentEntity =  clienteRepository.findById(id);

        if (currentEntity.isPresent()) {
            return clienteRepository.save(new Cliente(nomeRazao, cpfCnpj, getEmpresa(), getUsuario()));
        }


    }
}
