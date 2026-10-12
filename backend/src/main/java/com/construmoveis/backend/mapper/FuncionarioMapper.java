package com.construmoveis.backend.mapper;

import com.construmoveis.backend.dto.FuncionarioRequestDTO;
import com.construmoveis.backend.dto.FuncionarioResponseDTO;
import com.construmoveis.backend.entity.Funcionario;

import java.util.ArrayList;
import java.util.List;

public class FuncionarioMapper {

    public static List<FuncionarioResponseDTO> toResponse(List<Funcionario> funcionarios) {
        List<FuncionarioResponseDTO> response = new ArrayList<>();

        for (Funcionario funcionario : funcionarios) {
            response.add(toResponse(funcionario));
        }
        return response;
    }

    public static FuncionarioResponseDTO toResponse(Funcionario entity){
        if (entity == null){
            return null;
        }

        return new FuncionarioResponseDTO(
                entity.getId(),
                entity.getNome(),
                entity.getCpf(),
                entity.getEmail(),
                entity.getTelefone(),
                entity.getRegiao(),
                entity.getCargo(),
                entity.getAtivo()
        );
    }

    public static Funcionario toEntity(FuncionarioRequestDTO request){
        if (request == null){
            return null;
        }

        return new Funcionario(
                null,
                request.getNome(),
                request.getCpf(),
                request.getEmail(),
                request.getTelefone(),
                request.getRegiao(),
                request.getCargo(),
                request.getSenha(),
                null
        );
    }
}
