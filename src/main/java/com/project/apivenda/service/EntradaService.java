package com.project.apivenda.service;

import com.project.apivenda.dto.request.EntradaRequest;
import com.project.apivenda.dto.response.EntradaResponse;
import com.project.apivenda.entity.Cliente;
import com.project.apivenda.entity.Empresa;
import com.project.apivenda.entity.Entrada;
import com.project.apivenda.entity.Usuario;
import com.project.apivenda.exception.ErrorCode;
import com.project.apivenda.exception.exceptions.BusinessException;
import com.project.apivenda.mapper.EntradaMapper;
import com.project.apivenda.repository.EntradaRepository;
import com.project.apivenda.repository.UsuarioRepository;
import com.project.apivenda.service.validator.EntradaValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EntradaService {

    private final EntradaRepository entradaRepository;
    private final EntradaValidator entradaValidator;
    private final EntradaMapper mapper;
    private final UsuarioRepository usuarioRepository;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;

    private Usuario getUsuario() {
        String email = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        return usuarioRepository.findByUsername(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    private Empresa getEmpresa() {
        return getUsuario().getEmpresa();
    }

    public Entrada findById(Long id) {
        return entradaValidator.validateById(id);
    }

    public Page<EntradaResponse> search(String search, Pageable pageable) {
        Page<Entrada> entradas;

        try {
            if (search != null && !search.trim().isEmpty()) {
                entradas = entradaRepository.search(
                        search.trim(),
                        getEmpresa(),
                        pageable
                );
            } else {
                entradas = entradaRepository.findByEmpresa(getEmpresa(), pageable);
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.INTERNAL_ERROR);
        }

        return entradas.map(mapper::toDto);
    }

    public EntradaResponse insert (EntradaRequest dto) {
        Cliente fornecedor = clienteService.insertOrUpdate(
                dto.getFornecedorId(),
                dto.getNomeRazao(),
                true,
                dto.getCpfCnpj()
        );

        var entity = mapper.toEntity(dto);
        entity.setFornecedor(fornecedor);
        entity.setEmpresa(fornecedor.getEmpresa());
        entity.setUsuarioCadastro(getUsuario());
        entity.setUsuarioAtualizacao(getUsuario());

        entity.getItens().forEach(item -> {
            item.setEmpresa(getEmpresa());
            item.setUsuarioCadastro(getUsuario());
            item.setUsuarioAtualizacao(getUsuario());
            item.setProduto(produtoService.insertOrUpdate(

            ));
        });

        entity = entradaRepository.save(entity);
        return mapper.toDto(entity);
    }
}
