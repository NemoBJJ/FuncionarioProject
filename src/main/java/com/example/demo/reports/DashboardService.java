package com.example.demo.reports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.FuncionarioRepository;

import java.util.*;

@Service
public class DashboardService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public Map<String, Object> getDadosDashboard() {
        Map<String, Object> dados = new HashMap<>();

        // Consulta ao banco para obter os dados dos cargos
        List<Object[]> cargosFromDb = funcionarioRepository.findFuncionariosPorCargo();
        List<Map<String, Object>> cargos = new ArrayList<>();

        for (Object[] cargo : cargosFromDb) {
            Map<String, Object> cargoData = new HashMap<>();
            cargoData.put("cargo", cargo[0]); // Nome do cargo
            cargoData.put("quantidade", cargo[1]); // Quantidade de funcionários
            cargos.add(cargoData);
        }

        dados.put("cargos", cargos); // Adiciona a lista de cargos no mapa de dados
        return dados;
    }
}
