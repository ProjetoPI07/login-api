package com.construmoveis.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class FuncionarioResponseDTO {
    @Schema(description = "Identificador do funcionário", example = "1")
    private Integer id;
    @Schema(description = "Nome do funcionário", example = "Maria Lima")
    private String nome;
    @Schema(description = "CPF do funcionário", example = "555.444.333-11")
    private String cpf;
    @Schema(description = "Email do funcionário", example = "maria@example.com")
    private String email;
    @Schema(description = "Número de telefone do funcionário", example = "11-94365-9632")
    private String telefone;
    @Schema(description = "Região de vendas do funcionário", example = "Centro")
    private String regiao;
    @Schema(description = "Cargo do funcionário", example = "Vendas")
    private String cargo;
    @Schema(description = "Status do funcionário no sistema", example = "True/False")
    private Boolean ativo;

    public FuncionarioResponseDTO() {
    }

    public FuncionarioResponseDTO(Integer id, String nome, String cpf, String email, String telefone, String regiao, String cargo, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.regiao = regiao;
        this.cargo = cargo;
        this.ativo = ativo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
