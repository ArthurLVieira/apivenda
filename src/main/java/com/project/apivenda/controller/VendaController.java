package com.project.apivenda.controller;

import com.project.apivenda.dto.ApiResponse;
import com.project.apivenda.dto.request.VendaRequest;
import com.project.apivenda.dto.response.VendaResponse;
import com.project.apivenda.service.VendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequestMapping("/api/venda")
@RequiredArgsConstructor
public class VendaController {
    private final VendaService vendaService;

    @PostMapping
    public ResponseEntity<ApiResponse<VendaResponse>> insert(
            @RequestBody @Valid VendaRequest venda
    ) {
        return ResponseEntity.ok(ApiResponse.<VendaResponse>builder()
                .success(true)
                .data(vendaService.insert(venda))
                .message("Cadastro realizado com sucesso.")
                .traceId(UUID.randomUUID().toString())
                .path("/api/venda")
                .timestamp(LocalDateTime.now())
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<VendaResponse>> update(
            @PathVariable Long id,
            @RequestBody @Valid VendaRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.<VendaResponse>builder()
                .success(true)
                .data(vendaService.insert(request))
                .message("Registro alterado com secesso")
                .traceId(UUID.randomUUID().toString())
                .path("api/venda/" + id)
                .timestamp(LocalDateTime.now())
                .build());
    }
}
