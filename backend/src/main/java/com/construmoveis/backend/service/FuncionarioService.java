package com.construmoveis.backend.service;

import com.construmoveis.backend.dto.FuncionarioPatchDTO;
import com.construmoveis.backend.entity.Funcionario;
import com.construmoveis.backend.exception.FuncionarioNaoExiste;
import com.construmoveis.backend.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public List<Funcionario> listarFuncionarios(){
        return funcionarioRepository.findAll();
    }

    public Funcionario listarFuncionarioPorId(Integer id){
        validarId(id);
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(id);
        return funcionarioOptional.get();
    }

    public Funcionario cadastrarFuncionario(Funcionario funcionarioParaCriar){
        funcionarioParaCriar.setAtivo(true);
        return funcionarioRepository.save(funcionarioParaCriar);
    }

    public Funcionario atualizarFuncionario(FuncionarioPatchDTO funcionarioParaAtualizar, Integer id){
        Funcionario funcionario = listarFuncionarioPorId(id);

        if (funcionarioParaAtualizar.getNome() != null){
            funcionario.setNome(funcionarioParaAtualizar.getNome());
        }
        if (funcionarioParaAtualizar.getEmail() != null){
            funcionario.setEmail(funcionarioParaAtualizar.getEmail());
        }
        if (funcionarioParaAtualizar.getTelefone() != null){
            funcionario.setTelefone(funcionarioParaAtualizar.getTelefone());
        }
        if (funcionarioParaAtualizar.getRegiao() != null){
            funcionario.setRegiao(funcionarioParaAtualizar.getRegiao());
        }
        if (funcionarioParaAtualizar.getCargo() != null){
            funcionario.setCargo(funcionarioParaAtualizar.getCargo());
        }
        if (funcionarioParaAtualizar.getSenha() != null){
            funcionario.setSenha(funcionarioParaAtualizar.getSenha());
        }
        return funcionarioRepository.save(funcionario);
    }

    public void desativarFuncionario(Integer id){
        Funcionario funcionario = listarFuncionarioPorId(id);
        funcionario.setAtivo(false);
        funcionarioRepository.save(funcionario);
    }

    private void validarId(Integer id){
        if (!funcionarioRepository.existsById(id)){
            throw new FuncionarioNaoExiste();
        }
    }
}
