package com.example.demo.controller;

import com.example.demo.entity.Funcionario;
import com.example.demo.reports.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    @Autowired
    private DashboardService dashboardService;

    // Endpoint para listar funcionários com paginação
    @GetMapping
    public Page<Funcionario> listarFuncionarios(Pageable pageable) {
        return dashboardService.getFuncionariosPaginados(pageable);
    }
}
