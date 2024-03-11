package com.example.ProjetoUniSomaAPI.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioDTO {

    private long cpf;
    
    private double novoSalario;
    
    private double valorReajuste;
    
    private String percentualReajuste;

    public FuncionarioDTO(FuncionarioUpdateDTO data){
        this.cpf = data.cpf();
        this.novoSalario = data.novoSalario();
        this.valorReajuste = data.valorReajuste();
        this.percentualReajuste = data.percentualReajuste();
    }
}
