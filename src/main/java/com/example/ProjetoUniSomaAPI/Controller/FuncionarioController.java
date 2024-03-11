package com.example.ProjetoUniSomaAPI.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ProjetoUniSomaAPI.Entity.Funcionario;
import com.example.ProjetoUniSomaAPI.Entity.FuncionarioDTO;
import com.example.ProjetoUniSomaAPI.Entity.FuncionarioRequestDTO;
import com.example.ProjetoUniSomaAPI.Entity.FuncionarioResponseDTO;
import com.example.ProjetoUniSomaAPI.Entity.FuncionarioUpdateDTO;
import com.example.ProjetoUniSomaAPI.Repository.FuncionarioRepository;

@RestController
@RequestMapping("/api")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository repository;

    @GetMapping("/buscartodos")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<FuncionarioResponseDTO> getAll() {
        List<FuncionarioResponseDTO> funcionarioLista = repository.findAll().stream().map(FuncionarioResponseDTO::new)
                .toList();
        return funcionarioLista;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/salvarfuncionario")
    public ResponseEntity<String> saveFuncionarios(@RequestBody FuncionarioRequestDTO data) {
 

        Funcionario funcionarioData = new Funcionario(data);
        repository.save(funcionarioData);
        return ResponseEntity.ok("Funcionário inserido com sucesso!");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/att-salario/{cpf}")
    public ResponseEntity<String> atualizarSalario(@PathVariable Long cpf,
            @RequestBody FuncionarioDTO funcionarioDTO) {

            Optional<Funcionario> optionalFuncionario = repository.findByCpf(cpf);

        if (optionalFuncionario.isPresent()) {

            Funcionario funcionario = optionalFuncionario.get();

            double salarioAtual = funcionario.getSalario();
            double valorReajuste = funcionarioDTO.getValorReajuste();

            long percentualReajuste;
            if (valorReajuste <= 400.00) {
                percentualReajuste = 15;
            } else if (valorReajuste <= 800.00) {
                percentualReajuste = 12;
            } else if (valorReajuste <= 1200.00) {
                percentualReajuste = 10;
            } else if (valorReajuste <= 2000.00) {
                percentualReajuste = 7;
            } else {
                percentualReajuste = 4;
            }

            double novoSalario = salarioAtual + valorReajuste;

            funcionario.setSalario(novoSalario);

            repository.save(funcionario);

            String percentualReajusteComSimbolo = percentualReajuste + "%";

            String novoSalarioFormatado = String.format("%.2f", novoSalario).replace(",", ".");

            String valorReajusteFormatado = String.format("%.2f", valorReajuste).replace(",", ".");

            FuncionarioUpdateDTO updateDTO = new FuncionarioUpdateDTO(cpf, novoSalario, valorReajuste,
                    percentualReajusteComSimbolo);

            String mensagem = "CPF: " + cpf + "\n" +
                    "Novo salário: R$ " + novoSalarioFormatado + "\n" +
                    "Reajuste ganho: R$ " + valorReajusteFormatado + "\n" +
                    "Em percentual: " + percentualReajuste + " %";

            return ResponseEntity.ok(mensagem);
        } else {
            String mensagem = "CPF: " + cpf + " não encontrado!";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagem);
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/imposto-renda/{cpf}")
    public ResponseEntity<String> calcularImpostoRenda(@PathVariable long cpf){
        
            Optional<Funcionario> optionalFuncionario = repository.findByCpf(cpf);

        if (optionalFuncionario.isPresent())  {
            Funcionario funcionario = optionalFuncionario.get();
            double salario = funcionario.getSalario();
            double impostoRenda;

            if (salario <= 2000.00) {
                impostoRenda = 0.00;
            } else if (salario <= 3000.00) {
                impostoRenda = (salario - 2000.00) * 0.08;
            } else if (salario <= 4500.00) {
                impostoRenda = (1000.00 * 0.08) + ((salario - 3000.00) * 0.18);
            } else {
                impostoRenda = (1000.00 * 0.08) + (1500.00 * 0.18) + ((salario - 4500.00) * 0.28);
            }

            String valorImpostoFormatado = String.format("%.2f", impostoRenda).replace(",", ".");

            String mensagem;
            if (impostoRenda == 0.00) {
                mensagem = "CPF: " + cpf + "\n" +
                        "Isento";
            } else {
                mensagem = "CPF: " + cpf + "\n" +
                        "Imposto de Renda: R$ " + valorImpostoFormatado;
            }

            return ResponseEntity.ok(mensagem);
        } else {
            String mensagem = "CPF: " + cpf + " não encontrado!";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagem);
        }

    }

    

}