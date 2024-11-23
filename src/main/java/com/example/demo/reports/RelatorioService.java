package com.example.demo.reports;

import com.example.demo.model.RelatorioCargo;
import com.example.demo.model.RelatorioDepartamento;
import com.example.demo.repository.FuncionarioRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelatorioService {

    private final FuncionarioRepository funcionarioRepository;

    public RelatorioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    @Cacheable(value = "relatoriosPorCargo", key = "'cargo'")
    public List<RelatorioCargo> obterRelatoriosPorCargo() {
        System.out.println("Método obterRelatoriosPorCargo chamado e executado");
        return funcionarioRepository.countFuncionariosPorCargo();
    }

    @Cacheable(value = "relatoriosPorDepartamento", key = "'departamento'")
    public List<RelatorioDepartamento> obterRelatoriosPorDepartamento() {
        System.out.println("Método obterRelatoriosPorDepartamento chamado e executado");
        return funcionarioRepository.somaSalariosPorDepartamento();
    }
}
