package com.project.apivenda.controller;

import com.project.apivenda.controller.generics.Generic;
import com.project.apivenda.dto.request.RegisterRequest;
import com.project.apivenda.dto.response.RegisterResponse;
import com.project.apivenda.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("register")
@RequiredArgsConstructor
public class RegisterController implements Generic {
    private final RegisterService service;

    @PostMapping
    public ResponseEntity<RegisterResponse> register(
            @RequestBody RegisterRequest request
    ) {
        var response = service.register(request);
        return ResponseEntity.created(getUri(response.getEmpresa().getId())).body(response);
    }
}
