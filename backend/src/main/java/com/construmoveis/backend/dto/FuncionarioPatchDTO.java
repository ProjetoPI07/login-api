package com.construmoveis.backend.dto;

public class FuncionarioPatchDTO {
    private String nome;
    private String email;
    private String telefone;
    private String regiao;
    private String cargo;
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
