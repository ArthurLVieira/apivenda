package com.project.apivenda.exception;

import com.project.apivenda.dto.ApiError;
import lombok.Getter;

@Getter
public enum ErrorCode {
    AUTH_INVALID_CREDENTIALS("AUTH-001", "Credenciais inválidas."),
    AUTH_UNAUTHORIZED("AUTH-002", "Não autorizado."),
    USER_NOT_FOUND("USER-001", "Usuário não encontrado."),
    USER_INVALID("USER-002", "Usuário imválido, já cadastrado na base de dados."),
    USER_EMAIL_EXISTING("USER-0003", "Email já cadastrado por outro usuário"),
    USER_SENHA_INVALID("USER-004", "Senha atual incorreta"),
    USER_SENHA_NOT_EQUALS("USER-005", "Nova senha e confirmação não conferem"),
    USER_SENHA_EQUALS("USER-006", "A nova senha deve ser diferente da atual"),
    USER_EMAIL_NOT_FOUND("USER-007", "Email não encontrado"),
    VALIDATION_ERROR("GEN-001", "Erro de validação."),
    INTERNAL_ERROR("GEN-999", "Erro interno."),
    INTERNAL_SERVER_ERROR("GEN-998", "Erro interno no servidor."),
    ROLE_NOT_FOUND("ROLE-001", "Permissão não encontrada."),
    PIN_INVALID("PIN-001", "PIN inválido"),
    PIN_EXPIRED("PIN-002", "PIN expirado."),
    CLIENT_NOT_FOUND("PIN-001", "Cliente não encontrado."),
    CLIENT_HAS_EXISTS("PIN-002", "Cliente inválido, já cadastrado na base de dados."),
    CLIENT_CPF_HAS_EXISTS("PIN-003", "Cliente inválido, CPF já cadastrado na base de dados."),;

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiError toError() {
        return new ApiError(code, message);
    }
}
