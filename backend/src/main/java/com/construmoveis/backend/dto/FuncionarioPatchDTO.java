package com.construmoveis.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class FuncionarioPatchDTO {
    @Schema(description = "Nome do funcionário", example = "Maria Lima")
    private String nome;
    @Schema(description = "Email  do funcionário", example = "maria@example.com")
    private String email;
    @Schema(description = "Número de telefone do funcionário", example = "11-94365-9632")
    private String telefone;
    @Schema(description = "Região de vendas do funcionário", example = "Centro")
    private String regiao;
    @Schema(description = "Cargo do funcionário", example = "Vendas")
    private String cargo;
    @Schema(description = "Senha do funcionário", example = "maria123@")
    private String senha;

    public FuncionarioPatchDTO() {
    }

    public FuncionarioPatchDTO(String nome, String email, String telefone, String regiao, String cargo, String senha) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.regiao = regiao;
        this.cargo = cargo;
        this.senha = senha;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
