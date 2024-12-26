package com.example.demo.controller;

import com.example.demo.entity.Funcionario;
import com.example.demo.model.FuncionarioSalarioDTO;
import com.example.demo.reports.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/funcionarios") // Endpoints para JSON
@CrossOrigin(origins = "http://localhost:3000") // Permite requisições do frontend
public class FuncionarioController {

    @Autowired
    private DashboardService dashboardService;

    // Lista completa de funcionários (JSON)
    @GetMapping
    public Page<Funcionario> listarTodosFuncionariosJson(Pageable pageable) {
        return dashboardService.getFuncionariosPaginados(pageable);
    }

    // Funcionários com nome e salário (JSON)
    @GetMapping("/salarios-json")
    public Page<FuncionarioSalarioDTO> listarFuncionariosComSalarioJson(Pageable pageable) {
        return dashboardService.getFuncionariosComSalario(pageable);
    }
}
