package com.construmoveis.backend.controller;

import com.construmoveis.backend.dto.*;
import com.construmoveis.backend.entity.Funcionario;
import com.construmoveis.backend.mapper.FuncionarioMapper;
import com.construmoveis.backend.service.FuncionarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
public class FuncionarioController {

    public static final String COOKIE_NOME = "authToken";

    @Value("${jwt.validity}")
    private long jwtValidity;

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDTO> listarFuncionarioPorId(@PathVariable Integer id) {
        Funcionario funcionario = funcionarioService.listarFuncionarioPorId(id);
        return ResponseEntity.status(200).body(FuncionarioMapper.toResponse(funcionario));
    }

    @PostMapping
    public ResponseEntity<FuncionarioResponseDTO> cadastrarFuncionario(@RequestBody @Valid FuncionarioRequestDTO request) {
        Funcionario funcionario = FuncionarioMapper.toEntity(request);
        Funcionario salvo = funcionarioService.cadastrarFuncionario(funcionario);
        return ResponseEntity.status(201).body(FuncionarioMapper.toResponse(salvo));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDTO> atualizarFuncionario(@PathVariable Integer id, @RequestBody FuncionarioPatchDTO request) {
        Funcionario atualizado = funcionarioService.atualizarFuncionario(request, id);
        return ResponseEntity.status(200).body(FuncionarioMapper.toResponse(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativarFuncionario(@PathVariable Integer id) {
        funcionarioService.desativarFuncionario(id);
        return ResponseEntity.status(204).build();
    }

    @PostMapping("/login")
    public ResponseEntity<FuncionarioTokenDTO> login(@RequestBody @Valid FuncionarioLoginDTO funcionarioLoginDTO, HttpServletResponse response) {
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
