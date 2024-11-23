package com.example.demo.model;

public class RelatorioDepartamento {
    private String departamento;
    private Double somaSalarios;

    public RelatorioDepartamento(String departamento, Double somaSalarios) {
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

    public Double getSomaSalarios() {
        return somaSalarios;
    }

    public void setSomaSalarios(Double somaSalarios) {
        this.somaSalarios = somaSalarios;
    }
}
