package com.project.apivenda.service;

import com.project.apivenda.dto.request.ClienteRequest;
import com.project.apivenda.dto.response.ClienteResponse;
import com.project.apivenda.entity.Cliente;
import com.project.apivenda.entity.Empresa;
import com.project.apivenda.entity.Usuario;
import com.project.apivenda.exception.ErrorCode;
import com.project.apivenda.exception.exceptions.BusinessException;
import com.project.apivenda.mapper.ClienteMapper;
import com.project.apivenda.repository.ClienteRepository;
import com.project.apivenda.repository.UsuarioRepository;
import com.project.apivenda.service.validator.ClienteValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final ClienteMapper mapper;
    private final ClienteValidator validator;

    private Usuario getUsuario() {
        String email = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        return usuarioRepository.findByUsername(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    private Empresa getEmpresa() {
        return getUsuario().getEmpresa();
    }

    public ClienteResponse findById(Long id) {
        return mapper.toDto(validator.validateEntityId(id));
    }

    public Page<ClienteResponse> search(
            Pageable pageable,
            String search
    ) {
        Page<Cliente> clientes;
        try {
            if (search != null && !search.trim().isEmpty()) {
                clientes = clienteRepository.search(
                        search.trim(),
                        getEmpresa(),
                        pageable
                );
            } else {
                clientes = clienteRepository.findAll(getEmpresa(), pageable);
            }
        }catch (Exception e) {
            throw new BusinessException(ErrorCode.INTERNAL_ERROR);
        }

        return clientes.map(mapper::toDto);
    }

    public ClienteResponse insert(ClienteRequest request) {
        Cliente entity = mapper.toEntity(request);
        entity.setUsuarioCadastro(getUsuario());
        entity.setUsuarioAtualizacao(getUsuario());
        entity.setEmpresa(getEmpresa());

        validator.validateEntity(entity, null);
        return mapper.toDto(clienteRepository.save(entity));
    }

    public ClienteResponse update(ClienteRequest request, Long id) {
        Cliente newEntity = mapper.toEntity(request);
        newEntity.setUsuarioAtualizacao(getUsuario());
        validator.validateEntity(newEntity, null);
        Cliente currentEntity = validator.validateEntityId(id);
        validator.updateData(newEntity, currentEntity);
        return mapper.toDto(clienteRepository.save(currentEntity));
    }

    public void deleteById(Long id) {
        var cliente = validator.validateEntityId(id);
        validator.validateCanDelete(cliente);
        clienteRepository.deleteById(id);
    }

    ClienteResponse updateStatus(Long id) {
        var entity =  validator.validateEntityId(id);
        entity.setStatus(!entity.isStatus());
        entity.setUsuarioAtualizacao(getUsuario());

        return mapper.toDto(clienteRepository.save(entity));
    }

}
