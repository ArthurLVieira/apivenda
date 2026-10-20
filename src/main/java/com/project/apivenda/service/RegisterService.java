package com.project.apivenda.service;

import com.project.apivenda.dto.request.RegisterRequest;
import com.project.apivenda.dto.response.RegisterResponse;
import com.project.apivenda.entity.Role;
import com.project.apivenda.mapper.EmpresaMapper;
import com.project.apivenda.mapper.UsuarioMapper;
import com.project.apivenda.repository.EmpresaRepository;
import com.project.apivenda.repository.UsuarioRepository;
import com.project.apivenda.service.validator.EmpresaValidator;
import com.project.apivenda.service.validator.RoleValidator;
import com.project.apivenda.service.validator.UsuarioValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RegisterService {
    private final EmpresaRepository empresaRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final UsuarioValidator usuarioValidator;
    private final EmpresaMapper empresaMapper;
    private final EmpresaValidator empresaValidator;
    private final RoleValidator roleValidator;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        var empresa = empresaMapper.toEntity(request.getEmpresa());
        empresaValidator.validateEntity(empresa, null);
        empresa = empresaRepository.save(empresa);

        Set<Role> roles = new HashSet<>();
        roles.add(roleValidator.validateEntityByNome("ADMIN"));
        roles.add(roleValidator.validateEntityByNome("USER"));

        var usuario = usuarioMapper.toEntity(request.getUsuario());
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuario.setRoles(roles);
        usuario.setEmpresa(empresa);
        usuarioValidator.validateEntity(usuario, null);
        usuario =  usuarioRepository.save(usuario);

        return RegisterResponse.builder()
                .usuario(usuarioMapper.toDto(usuario))
                .empresa(empresaMapper.toDto(empresa))
                .build();
    }
}
