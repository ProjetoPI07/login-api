package com.construmoveis.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(hidden = true)
public class FuncionarioTokenDTO {
    @Schema(description = "Identificador do funcionário", example = "1")
    private Integer userId;
    @Schema(description = "Nome do funcionário", example = "Maria Lima")
    private String nome;
    @Schema(description = "Email do funcionário", example = "maria@example.com")
    private String email;
    @Schema(description = "Token gerado pelo JWT")
    private String token;

    public FuncionarioTokenDTO() {
    }

    public FuncionarioTokenDTO(Integer userId, String nome, String email, String token) {
        this.userId = userId;
        this.nome = nome;
        this.email = email;
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
