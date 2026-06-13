package com.example.demo.reports;

import com.example.demo.entity.Funcionario;
import com.example.demo.model.FuncionarioSalarioDTO;
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

    // Paginação de funcionários com salário
    public Page<FuncionarioSalarioDTO> getFuncionariosComSalario(Pageable pageable) {
        return funcionarioRepository.findFuncionariosComSalario(pageable);
    }

    // Dados agregados para o dashboard
    public Map<String, Object> getDadosDashboard() {
        Map<String, Object> dados = new HashMap<>();

        List<Object[]> cargosFromDb = funcionarioRepository.findFuncionariosPorCargo();
        List<Map<String, Object>> cargos = new ArrayList<>();

        for (Object[] result : cargosFromDb) {
            Map<String, Object> cargoData = new HashMap<>();
            cargoData.put("cargo", result[0]);
            cargoData.put("quantidade", result[1]);
            cargos.add(cargoData);
        }

        dados.put("cargos", cargos);

        Map<String, Long> cargoCounts = new HashMap<>();
        for (Object[] result : cargosFromDb) {
            String cargo = (String) result[0];
            Long count = (Long) result[1];
            cargoCounts.put(cargo, count);
        }
        dados.put("quantidadePorCargo", cargoCounts);

        return dados;
    }

    // Buscar funcionário por ID
    public Optional<Funcionario> buscarFuncionarioPorId(Long id) {
        return funcionarioRepository.findById(id);
    }

    // Salvar funcionário
    public Funcionario salvarFuncionario(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    // Listar todos os funcionários (sem paginação)
    public List<Funcionario> listarTodosFuncionarios() {
        return funcionarioRepository.findAll();
    }
}