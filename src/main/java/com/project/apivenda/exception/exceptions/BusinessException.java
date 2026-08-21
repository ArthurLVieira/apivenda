package com.project.apivenda.exception.exceptions;

import com.project.apivenda.exception.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.name());
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return errorCode.getMessage();
    }

    public String getErrorCode() {
        return errorCode.getCode();
    }
}
