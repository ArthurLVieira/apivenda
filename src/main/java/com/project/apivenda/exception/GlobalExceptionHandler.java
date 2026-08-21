package com.project.apivenda.exception;

import com.project.apivenda.dto.ApiError;
import com.project.apivenda.dto.ApiResponse;
import com.project.apivenda.exception.exceptions.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
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
                        .code(ex.getMessage())
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
}
