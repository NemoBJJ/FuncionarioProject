package com.example.demo.model;

import java.math.BigDecimal;

public class RelatorioDepartamento {
    private String departamento;
    private BigDecimal somaSalarios;

    // Construtor padrão necessário para o Hibernate
    public RelatorioDepartamento() {
    }

    // Construtor utilizado na consulta JPQL
    public RelatorioDepartamento(String departamento, BigDecimal somaSalarios) {
        this.departamento = departamento;
        this.somaSalarios = somaSalarios;
    }

    // Getters e Setters
    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public BigDecimal getSomaSalarios() {
        return somaSalarios;
    }

    public void setSomaSalarios(BigDecimal somaSalarios) {
        this.somaSalarios = somaSalarios;
    }

    @Override
    public String toString() {
        return "RelatorioDepartamento{" +
                "departamento='" + departamento + '\'' +
                ", somaSalarios=" + somaSalarios +
                '}';
    }
}
