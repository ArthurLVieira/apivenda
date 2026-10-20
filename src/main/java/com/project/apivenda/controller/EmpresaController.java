package com.project.apivenda.controller;

import com.project.apivenda.dto.request.EmpresaRequest;
import com.project.apivenda.dto.response.EmpresaResponse;
import com.project.apivenda.service.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("empresa")
@RequiredArgsConstructor
public class EmpresaController {
    private final EmpresaService service;

    @PutMapping("{id}")
    public ResponseEntity<EmpresaResponse> update(
            @PathVariable Long id,
            @RequestBody EmpresaRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.update(request, id));
    }
}
