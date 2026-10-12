package com.construmoveis.backend.repository;

import com.construmoveis.backend.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository  extends JpaRepository<Funcionario, Integer> {
}
