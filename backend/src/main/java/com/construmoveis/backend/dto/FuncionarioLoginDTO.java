package com.construmoveis.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class FuncionarioLoginDTO {

    @NotBlank
    @Email
    @Schema(description = "Email do usuário", example = "maria@example.com")
    private String email;

    @NotBlank
    @Schema(description = "Senha do usuário", example = "maria123@")
    private String senha;

    public FuncionarioLoginDTO() {
    }

    public FuncionarioLoginDTO(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
