package com.example.demo.model;

public class RelatorioCargo {
    private String cargo;
    private Long quantidadeFuncionarios;

    public RelatorioCargo(String cargo, Long quantidadeFuncionarios) {
        this.cargo = cargo;
        this.quantidadeFuncionarios = quantidadeFuncionarios;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Long getQuantidadeFuncionarios() {
        return quantidadeFuncionarios;
    }

    public void setQuantidadeFuncionarios(Long quantidadeFuncionarios) {
        this.quantidadeFuncionarios = quantidadeFuncionarios;
    }
}
