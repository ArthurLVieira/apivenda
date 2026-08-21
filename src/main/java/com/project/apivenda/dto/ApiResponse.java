package com.project.apivenda.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {

    private boolean success;
    private T data;
    private String message;
    private List<ApiError> errors;
    private String traceId;
    private String path;
    private LocalDateTime timestamp;
}
