package com.project.apivenda.exception;

import com.project.apivenda.dto.ApiError;
import com.project.apivenda.dto.ApiResponse;
import com.project.apivenda.exception.exceptions.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>>  handleBusinessException(
            BusinessException ex,
            HttpServletRequest request
    ) {
        List<ApiError> errors = List.of(
                ApiError.builder()
                        .code(ex.getErrorCode())
                        .message(ex.getMessage())
                        .build()
        );

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(false)
                .errors(errors)
                .traceId(UUID.randomUUID().toString())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>>  handleIllegalArgumentException(
            IllegalArgumentException ex,
            HttpServletRequest request
    ) {
        List<ApiError> errors = List.of(
                ApiError.builder()
                        .message(ex.getMessage())
                        .build()
        );

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(false)
                .message(ex.getMessage())
                .errors(errors)
                .traceId(UUID.randomUUID().toString())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ){
        List<ApiError> errors = new ArrayList<ApiError>();

        for(FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.add(ApiError.builder()
                    .code(fieldError.getCode())
                    .message(fieldError.getDefaultMessage())
                    .build());
        }

        if (errors.isEmpty()) {
            errors.add(ApiError.builder().message("Dados inválidos").build());
        }

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(false)
                .errors(errors)
                .traceId(UUID.randomUUID().toString())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
