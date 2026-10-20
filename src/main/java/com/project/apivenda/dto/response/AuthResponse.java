package com.project.apivenda.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse<T> {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long expiresIn;
    private T data;
}
