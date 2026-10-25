package com.construmoveis.backend.controller;

import com.construmoveis.backend.dto.*;
import com.construmoveis.backend.entity.Funcionario;
import com.construmoveis.backend.mapper.FuncionarioMapper;
import com.construmoveis.backend.service.FuncionarioService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/funcionarios")
@Tag(name = "Login Controller", description = "Operações que gerenciam o login do funcionário")
public class FuncionarioController {

    public static final String COOKIE_NOME = "authToken";

    @Value("${jwt.validity}")
    private long jwtValidity;

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @Operation(
            summary = "Busca de funcionário por ID",
            description = "Busca um funcionário pelo ID no banco de dados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDTO> listarFuncionarioPorId(
            @Parameter(description = "Identificador do funcionário", example = "1")
            @PathVariable Integer id) {
        Funcionario funcionario = funcionarioService.listarFuncionarioPorId(id);
        return ResponseEntity.status(200).body(FuncionarioMapper.toResponse(funcionario));
    }

    @Operation(
            summary = "Cadastrar funcionário",
            description = "Cadastra um novo usuário no banco de dados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso."),
    })
    @PostMapping
    public ResponseEntity<FuncionarioResponseDTO> cadastrarFuncionario(
            @Parameter(description = "FuncionarioRequestDTO", example = " \"nome\": \"Maria\",\n" +
                    "  \"cpf\": \"555.444.333-11\",\n" +
                    "  \"email\": \"maria@example.com\",\n" +
                    "  \"telefone\": \"11-94365-9632\",\n" +
                    "  \"regiao\": \"Centro\",\n" +
                    "  \"cargo\": \"Vendedor\",\n" +
                    "  \"senha\": \"maria123@\"")
            @RequestBody @Valid FuncionarioRequestDTO request) {
        Funcionario funcionario = FuncionarioMapper.toEntity(request);
        Funcionario salvo = funcionarioService.cadastrarFuncionario(funcionario);
        return ResponseEntity.status(201).body(FuncionarioMapper.toResponse(salvo));
    }

    @Operation(
            summary = "Atualizar funcionário",
            description = "Atualiza o funcionário pesquisado pelo ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Informações do funcionário atualizadas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado"),
    })
    @PatchMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDTO> atualizarFuncionario(
            @Parameter(description = "Identificador do funcionário", example = "1")
            @PathVariable Integer id,
            @Parameter(description = "FuncionarioPatchDTO", example = " \"nome\": \"Maria\",\n" +
                    "  \"email\": \"maria@example.com\",\n" +
                    "  \"telefone\": \"11-94365-9632\",\n" +
                    "  \"regiao\": \"Centro\",\n" +
                    "  \"cargo\": \"Vendedor\",\n" +
                    "  \"senha\": \"maria123@\"")
            @RequestBody FuncionarioPatchDTO request) {
        Funcionario atualizado = funcionarioService.atualizarFuncionario(request, id);
        return ResponseEntity.status(200).body(FuncionarioMapper.toResponse(atualizado));
    }

    @Operation(
            summary = "Desativar funcionário",
            description = "Desativa o funcionário buscado pelo ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Funcionário desativo com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativarFuncionario(
            @Parameter(description = "Identificador do funcionário", example = "1")
            @PathVariable Integer id) {
        funcionarioService.desativarFuncionario(id);
        return ResponseEntity.status(204).build();
    }

    @Operation(
            summary = "Login",
            description = "Faz login do funcionário"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login feito com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado"),
    })
    @PostMapping("/login")
    public ResponseEntity<FuncionarioTokenDTO> login(
            @Parameter(description = "FuncionarioLoginDTO", example = "\"email\": \"maria@example.com\",\n" +
                    "  \"senha\": \"maria123@\"")
            @RequestBody @Valid FuncionarioLoginDTO funcionarioLoginDTO, HttpServletResponse response) {
        FuncionarioTokenDTO autenticado = this.funcionarioService.autenticar(funcionarioLoginDTO);

        ResponseCookie cookie = ResponseCookie.from(COOKIE_NOME, autenticado.getToken())
                .httpOnly(true)
                .secure(false)
                .sameSite("Strict")
                .path("/")
                .maxAge(Duration.ofSeconds(jwtValidity))
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(autenticado);
    }

    @Operation(
            summary = "Logout",
            description = "Faz logout do usuário"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Logout realizado com sucesso."),
    })
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from(COOKIE_NOME, "")
                .httpOnly(true)
                .secure(false)
                .sameSite("Strict")
                .path("/")
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Lista todos os funcionários",
            description = "Lista todos funcionários cadastradas no banco de dados."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Funcionários encontrados"),
    })
    @GetMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<FuncionarioResponseDTO>> listarTodos() {
        List<Funcionario> funcionarios = this.funcionarioService.listarFuncionarios();

        if (funcionarios.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(FuncionarioMapper.toResponse(funcionarios));
    }
}
