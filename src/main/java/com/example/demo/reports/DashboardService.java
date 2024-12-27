package com.example.demo.reports;

import com.example.demo.entity.Funcionario;
import com.example.demo.model.FuncionarioSalarioDTO;
import com.example.demo.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    // Paginação de funcionários
    public Page<Funcionario> getFuncionariosPaginados(Pageable pageable) {
        return funcionarioRepository.findAll(pageable);
    }

    // Paginação de funcionários com salário
    public Page<FuncionarioSalarioDTO> getFuncionariosComSalario(Pageable pageable) {
        return funcionarioRepository.findFuncionariosComSalario(pageable);
    }

    // Dados agregados para o dashboard
    public Map<String, Object> getDadosDashboard() {
        Map<String, Object> dados = new HashMap<>();

        // Consulta ao banco para obter cargos e contagem de funcionários
        List<Object[]> cargosFromDb = funcionarioRepository.findFuncionariosPorCargo();
        List<Map<String, Object>> cargos = new ArrayList<>();

        for (Object[] result : cargosFromDb) {
            Map<String, Object> cargoData = new HashMap<>();
            cargoData.put("cargo", result[0]); // Nome do cargo
            cargoData.put("quantidade", result[1]); // Quantidade de funcionários
            cargos.add(cargoData);
        }

        // Adiciona os dados dos cargos ao mapa final
        dados.put("cargos", cargos);

        // Adiciona também a contagem agregada por cargo para outras possíveis utilizações
        Map<String, Long> cargoCounts = new HashMap<>();
        for (Object[] result : cargosFromDb) {
            String cargo = (String) result[0];
            Long count = (Long) result[1];
            cargoCounts.put(cargo, count);
        }
        dados.put("quantidadePorCargo", cargoCounts);

        // Adicione outras agregações ou informações aqui, se necessário
        return dados;
    }
}
