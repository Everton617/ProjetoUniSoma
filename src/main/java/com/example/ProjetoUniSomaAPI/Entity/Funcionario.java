package com.example.ProjetoUniSomaAPI.Entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Table(name = "funcionarios")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "cpf")
public class Funcionario {
    
    @Id
    @Column(unique = true)
    private long cpf;
    
    private String nome;
    
    private Date dt_nascimento;
    
    private long telefone;
    
    private String endereco;
    
    private double salario;

    public Funcionario(FuncionarioRequestDTO data){
        this.nome = data.nome();
        this.cpf = data.cpf();
        this.dt_nascimento = data.dt_nascimento();
        this.telefone = data.telefone();
        this.endereco = data.endereco();
        this.salario = data.salario();
    }

    


}
