package com.construmoveis.backend.service;

import com.construmoveis.backend.config.GerenciadorTokenJwt;
import com.construmoveis.backend.dto.FuncionarioLoginDTO;
import com.construmoveis.backend.dto.FuncionarioPatchDTO;
import com.construmoveis.backend.dto.FuncionarioTokenDTO;
import com.construmoveis.backend.entity.Funcionario;
import com.construmoveis.backend.exception.FuncionarioNaoExiste;
import com.construmoveis.backend.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final FuncionarioRepository funcionarioRepository;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

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
        String senhaCriptografada = passwordEncoder.encode(funcionarioParaCriar.getSenha());
        funcionarioParaCriar.setSenha(senhaCriptografada);
        return funcionarioRepository.save(funcionarioParaCriar);
    }

    public FuncionarioTokenDTO autenticar(FuncionarioLoginDTO funcionario) {

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                funcionario.getEmail(), funcionario.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Funcionario funcionarioAutenticado = funcionarioRepository.findByEmail(funcionario.getEmail()).orElseThrow(() ->
                        new ResponseStatusException(404, "Email do usuário não cadastrado", null));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return new FuncionarioTokenDTO(
                funcionarioAutenticado.getId(),
                funcionarioAutenticado.getNome(),
                funcionarioAutenticado.getEmail(),
                token
        );
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
