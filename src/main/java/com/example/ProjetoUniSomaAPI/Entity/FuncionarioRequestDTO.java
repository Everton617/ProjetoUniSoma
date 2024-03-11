package com.example.ProjetoUniSomaAPI.Entity;

import java.util.Date;

public record FuncionarioRequestDTO (String nome, long cpf, Date dt_nascimento, long telefone, String endereco, double salario) {

}
