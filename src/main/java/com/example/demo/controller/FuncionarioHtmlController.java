package com.example.demo.controller;

import com.example.demo.entity.Funcionario;
import com.example.demo.model.FuncionarioSalarioDTO;
import com.example.demo.reports.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/funcionarios-html")
public class FuncionarioHtmlController {

    @Autowired
    private DashboardService dashboardService;

    // Página HTML com lista completa de funcionários ou busca por ID
    @GetMapping
    public String listarOuBuscarFuncionarioHtml(
            @RequestParam(required = false) Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        if (id != null) {
            // Buscar funcionário por ID
            return dashboardService.buscarFuncionarioPorId(id)
                    .map(funcionario -> {
                        model.addAttribute("funcionario", funcionario);
                        return "funcionario-detalhes";
                    })
                    .orElseGet(() -> {
                        model.addAttribute("error", "Funcionário não encontrado");
                        return "funcionario-detalhes";
                    });
        }

        // Lista completa com paginação
        Page<Funcionario> funcionariosPage = dashboardService.getFuncionariosPaginados(PageRequest.of(page, size));
        model.addAttribute("page", funcionariosPage);
        model.addAttribute("funcionarios", funcionariosPage.getContent());

        return "funcionarios";
    }

    // Página HTML com lista de funcionários com nome e salário
    @GetMapping("/salarios")
    public String listarFuncionariosComSalarioHtml(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Page<FuncionarioSalarioDTO> funcionariosPage = dashboardService.getFuncionariosComSalario(PageRequest.of(page, size));
        model.addAttribute("page", funcionariosPage);
        model.addAttribute("funcionarios", funcionariosPage.getContent());

        return "funcionarios-salario";
    }
}

