package com.construmoveis.backend.service;

import com.construmoveis.backend.dto.FuncionarioDetalhesDTO;
import com.construmoveis.backend.entity.Funcionario;
import com.construmoveis.backend.exception.FuncionarioNaoExiste;
import com.construmoveis.backend.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws FuncionarioNaoExiste {

        Optional<Funcionario> funcionarioOpt = funcionarioRepository.findByEmail(username);

        if (funcionarioOpt.isEmpty()) {

            throw new UsernameNotFoundException(String.format("usuário: %s não encontrado", username));
        }

        return new FuncionarioDetalhesDTO(funcionarioOpt.get());
    }
}
