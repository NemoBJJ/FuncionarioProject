package com.example.demo.controller;

import com.example.demo.entity.Funcionario;
import com.example.demo.model.FuncionarioSalarioDTO;
import com.example.demo.reports.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/funcionarios-html") // Endpoints para Thymeleaf
public class FuncionarioHtmlController {

    @Autowired
    private DashboardService dashboardService;

    // Página HTML com lista completa de funcionários
    @GetMapping
    public String listarTodosFuncionariosHtml(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Page<Funcionario> funcionariosPage = dashboardService.getFuncionariosPaginados(PageRequest.of(page, size));
        model.addAttribute("page", funcionariosPage);
        model.addAttribute("funcionarios", funcionariosPage.getContent());

        return "funcionarios"; // Template Thymeleaf
    }

    // Página HTML com lista de funcionários com nome e salário
    @GetMapping("/salarios-html")
    public String listarFuncionariosComSalarioHtml(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Page<FuncionarioSalarioDTO> funcionariosPage = dashboardService.getFuncionariosComSalario(PageRequest.of(page, size));
        model.addAttribute("page", funcionariosPage);
        model.addAttribute("funcionarios", funcionariosPage.getContent());

        return "funcionarios-salario"; // Template Thymeleaf
    }
}


