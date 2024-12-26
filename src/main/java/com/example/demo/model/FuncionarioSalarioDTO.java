package com.example.demo.model;

import java.math.BigDecimal;

public class FuncionarioSalarioDTO {
    private Long id;
    private String nome;
    private BigDecimal salario;

    // Construtor padrão (obrigatório para o Hibernate)
    public FuncionarioSalarioDTO() {
    }

    // Construtor com parâmetros (necessário para a consulta HQL)
    public FuncionarioSalarioDTO(Long id, String nome, BigDecimal salario) {
        this.id = id;
        this.nome = nome;
        this.salario = salario;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }
}

