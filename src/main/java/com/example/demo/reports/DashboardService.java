package com.example.demo.reports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.FuncionarioRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public Map<String, Object> getDadosDashboard() {
        Map<String, Object> dados = new HashMap<>();

        // Obter quantidade de funcionários por cargo
        List<Object[]> cargos = funcionarioRepository.findFuncionariosPorCargo();
        List<Map<String, Object>> listaCargos = cargos.stream().map(cargo -> {
            Map<String, Object> map = new HashMap<>();
            map.put("cargo", cargo[0]);
            map.put("quantidade", cargo[1]);
            return map;
        }).toList();
        dados.put("cargos", listaCargos);

        // Obter média salarial por departamento
        List<Object[]> salarios = funcionarioRepository.findMediaSalarialPorDepartamento();
        List<Map<String, Object>> listaSalarios = salarios.stream().map(salario -> {
            Map<String, Object> map = new HashMap<>();
            map.put("departamento", salario[0]);
            map.put("mediaSalarial", salario[1]);
            return map;
        }).toList();
        dados.put("salarios", listaSalarios);

        return dados;
    }
}
