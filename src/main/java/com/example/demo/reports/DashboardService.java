package com.example.demo.reports;

import com.example.demo.entity.Funcionario;
import com.example.demo.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DashboardService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    // Paginação de funcionários
    public Page<Funcionario> getFuncionariosPaginados(Pageable pageable) {
        return funcionarioRepository.findAll(pageable);
    }

    // Dados agregados para o dashboard
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
