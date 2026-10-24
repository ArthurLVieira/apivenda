package com.project.apivenda.controller;

import com.project.apivenda.dto.ApiResponse;
import com.project.apivenda.dto.request.ClienteRequest;
import com.project.apivenda.dto.response.ClienteResponse;
import com.project.apivenda.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService service;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClienteResponse>> findById(@PathVariable Long id){
        return ResponseEntity.ok(ApiResponse.<ClienteResponse>builder()
                .success(true)
                .data(service.findById(id))
                .message("Cliente encontrado")
                .traceId(UUID.randomUUID().toString())
                .path("/api/cliente/" + id)
                .timestamp(LocalDateTime.now())
                .build()
        );
    }

    private static final Set<String> SORTABLE_FIELDS = Set.of(
            "id", "nomeRazao", "cpfCnpj", "dataNascimentoCriacao", "status", "fornecedor"
    );

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ClienteResponse>>> search(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "false") boolean fornecedor,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nomeCompleto,asc") String sort) {

        String[] sortParams = sort.split(",");
        Sort sortOrder = Sort.by(
                sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc")
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC,
                sortParams[0]);

        Pageable pageable = PageRequest.of(page, size, sortOrder);

        return ResponseEntity.ok(ApiResponse.<Page<ClienteResponse>>builder()
                .success(true)
                .data(service.search(pageable, search, fornecedor))
                .message("Pesquisa realizada com sucesso")
                .traceId(UUID.randomUUID().toString())
                .path("/api/cliente")
                .timestamp(LocalDateTime.now())
                .build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ClienteResponse>> insert(
            @RequestBody @Valid ClienteRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.<ClienteResponse>builder()
                .success(true)
                .data(service.insert(request))
                .message("Registro cadastrado com sucesso")
                .traceId(UUID.randomUUID().toString())
                .path("/api/cliente")
                .timestamp(LocalDateTime.now())
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClienteResponse>> update(
            @PathVariable Long id,
            @RequestBody @Valid ClienteRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.<ClienteResponse>builder()
                .success(true)
                .data(service.update(request, id))
                .message("Registro atualizado com secesso")
                .traceId(UUID.randomUUID().toString())
                .path("/api/cliente" + id)
                .timestamp(LocalDateTime.now())
                .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .message("Registro excluido com secesso")
                .traceId(UUID.randomUUID().toString())
                        .path("/api/cliente/" + id)
                .timestamp(LocalDateTime.now())
                .build()
        );
    }

    @PatchMapping("/switch/fornecedor/{id}")
    public ResponseEntity<ApiResponse<ClienteResponse>> changeFornecedor(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                ApiResponse.<ClienteResponse>builder()
                        .success(true)
                        .data(service.switchFornecedor(id))
                        .message("Registro atualizado com sucesso")
                        .traceId(UUID.randomUUID().toString())
                        .path("/api/cliente/switch/fornecedor/" + id)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PatchMapping("/switch/status/{id}")
    public ResponseEntity<ApiResponse<ClienteResponse>> changeStatus(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                ApiResponse.<ClienteResponse>builder()
                        .success(true)
                        .data(service.switchStatus(id))
                        .message("Registro atualizado com sucesso")
                        .traceId(UUID.randomUUID().toString())
                        .path("/api/cliente/switch/status/" + id)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }


}
