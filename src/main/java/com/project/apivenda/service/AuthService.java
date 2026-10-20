package com.project.apivenda.service;

import com.project.apivenda.dto.request.LoginRequest;
import com.project.apivenda.dto.request.RefreshTokenRequest;
import com.project.apivenda.dto.response.AuthResponse;
import com.project.apivenda.dto.response.UsuarioResponse;
import com.project.apivenda.entity.Usuario;
import com.project.apivenda.exception.ErrorCode;
import com.project.apivenda.exception.exceptions.BusinessException;
import com.project.apivenda.mapper.UsuarioMapper;
import com.project.apivenda.security.JwtUtils;
import com.project.apivenda.service.validator.UsuarioValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioValidator validator;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    public AuthResponse<UsuarioResponse> login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String accessToken = jwtUtils.generateToken(authentication);
            String refreshToken = jwtUtils.generateRefreshToken(loginRequest.getUsername());

            Usuario usuario = validator.validateEntityByEmail(loginRequest.getUsername());
            usuario.setUltimoAcesso(LocalDateTime.now());

            return AuthResponse.<UsuarioResponse>builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .tokenType("Bearer")
                    .expiresIn(jwtUtils.getExpirationTime())
                    .data(usuarioService.updateLogin(usuario))
                    .build();
        } catch (BadCredentialsException ex) {
            throw new BusinessException(ErrorCode.AUTH_INVALID_CREDENTIALS);
        }
    }

    @Transactional
    public AuthResponse<UsuarioResponse> refreshToken(RefreshTokenRequest request) {
        if (!jwtUtils.validateToken(request.getRefreshToken()) ||
                !jwtUtils.isRefreshToken(request.getRefreshToken())) {
            throw new BusinessException(ErrorCode.AUTH_INVALID_CREDENTIALS);
        }

        String username = jwtUtils.getUsernameFromToken(request.getRefreshToken());

        Usuario usuario = validator.validateEntityByEmail(username);

        String newAccessToken = jwtUtils.generateToken(username);
        String newRefreshToken = jwtUtils.generateRefreshToken(username);

        return AuthResponse.<UsuarioResponse>builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtUtils.getExpirationTime() / 1000)
                .data(usuarioMapper.toDto(usuario))
                .build();
    }

    public boolean validateToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }

    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication != null ? authentication.getName() : "desconhecido";
        SecurityContextHolder.clearContext();
    }
}
