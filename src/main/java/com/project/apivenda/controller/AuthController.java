package com.project.apivenda.controller;

import com.project.apivenda.dto.request.LoginRequest;
import com.project.apivenda.dto.request.RefreshTokenRequest;
import com.project.apivenda.dto.response.AuthResponse;
import com.project.apivenda.dto.response.UsuarioResponse;
import com.project.apivenda.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<AuthResponse<UsuarioResponse>> login(
            @Valid @RequestBody LoginRequest loginRequest
    ) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("refresh")
    public ResponseEntity<AuthResponse<UsuarioResponse>> refresh(
            @Valid @RequestBody RefreshTokenRequest request
    ) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }

    @PostMapping("logout")
    public ResponseEntity<Void> logout() {
        authService.logout();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken() {
        if (authService.validateToken())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(false);

        return ResponseEntity.ok().body(true);
    }
}
