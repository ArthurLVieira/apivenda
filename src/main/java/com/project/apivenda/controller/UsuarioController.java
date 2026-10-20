package com.project.apivenda.controller;

import com.project.apivenda.dto.request.UsuarioRequest;
import com.project.apivenda.dto.response.UsuarioResponse;
import com.project.apivenda.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("usuario")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService service;

    public ResponseEntity<UsuarioResponse> update(
            @PathVariable Long id,
            @RequestBody UsuarioRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.update(request, id));
    }
}
