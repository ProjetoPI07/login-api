package com.construmoveis.backend.service;

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

    public Funcionario atualizarFuncionario(Funcionario funcionarioParaAtualizar, Integer id){
        validarId(id);
        funcionarioParaAtualizar.setId(id);
        return funcionarioRepository.save(funcionarioParaAtualizar);
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
