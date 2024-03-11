package com.example.ProjetoUniSomaAPI.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ProjetoUniSomaAPI.Entity.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario,Long>{

    Optional<Funcionario> findByCpf(long cpf);
    
}
