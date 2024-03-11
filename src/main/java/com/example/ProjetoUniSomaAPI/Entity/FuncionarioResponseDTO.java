package com.example.ProjetoUniSomaAPI.Entity;

import java.util.Date;

public record FuncionarioResponseDTO(String nome, long cpf, Date dt_nascimento, long telefone, String endereco, double salario) {

    public FuncionarioResponseDTO(Funcionario funcionario){
        this(funcionario.getNome(),funcionario.getCpf(),funcionario.getDt_nascimento(),funcionario.getTelefone(),funcionario.getEndereco(),funcionario.getSalario());
    }
}
