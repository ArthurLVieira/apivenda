package com.project.apivenda.controller;

import com.project.apivenda.dto.ApiResponse;
import com.project.apivenda.dto.request.ProdutoRequest;
import com.project.apivenda.dto.response.ProdutoResponse;
import com.project.apivenda.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/produto")
@RequiredArgsConstructor
public class ProdutoController {
    private final ProdutoService service;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProdutoResponse>> findById(@PathVariable long id) {
        return ResponseEntity.ok(ApiResponse.<ProdutoResponse>builder()
                .success(true)
                .data(service.findById(id))
                .message("Produto encontrado")
                .traceId(UUID.randomUUID().toString())
                .path("/api/produto/" + id)
                .timestamp(LocalDateTime.now())
                .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProdutoResponse>>> search(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "descricao,asc") String sort) {

        String[] sortParams = sort.split(",");
        Sort sortOrder = Sort.by(
                sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc")
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC,
                sortParams[0]);

        Pageable pageable = PageRequest.of(page, size, sortOrder);

        return ResponseEntity.ok(ApiResponse.<Page<ProdutoResponse>>builder()
                .success(true)
                .data(service.search(search, pageable))
                .message("Pesquisa realizada com sucesso")
                .traceId(UUID.randomUUID().toString())
                .path("/api/produto")
                .timestamp(LocalDateTime.now())
                .build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProdutoResponse>> insert(
            @RequestBody @Valid ProdutoRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<ProdutoResponse>builder()
                .success(true)
                .data(service.insert(request))
                .message("Produto cadastrado")
                .traceId(UUID.randomUUID().toString())
                .path("/api/produto")
                .timestamp(LocalDateTime.now())
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProdutoResponse>> update(
            @PathVariable long id,
            @RequestBody @Valid ProdutoRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.<ProdutoResponse>builder()
                .success(true)
                .data(service.update(request, id))
                .message("Produto atualizado")
                .traceId(UUID.randomUUID().toString())
                .path("/api/produto/" + id)
                .timestamp(LocalDateTime.now())
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable long id) {
        service.deleteById(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .message("produto deletado")
                .traceId(UUID.randomUUID().toString())
                .path("/api/produto/" + id)
                .timestamp(LocalDateTime.now())
                .build());
    }

}
