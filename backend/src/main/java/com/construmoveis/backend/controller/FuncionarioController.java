package com.construmoveis.backend.controller;

import com.construmoveis.backend.dto.FuncionarioPatchDTO;
import com.construmoveis.backend.dto.FuncionarioRequestDTO;
import com.construmoveis.backend.dto.FuncionarioResponseDTO;
import com.construmoveis.backend.entity.Funcionario;
import com.construmoveis.backend.mapper.FuncionarioMapper;
import com.construmoveis.backend.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping
    public ResponseEntity<List<FuncionarioResponseDTO>> listarFuncionarios() {
        List<Funcionario> funcionarios = funcionarioService.listarFuncionarios();
        return ResponseEntity.status(200).body(FuncionarioMapper.toResponse(funcionarios));
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
}
