package com.example.demo.controller;

import com.example.demo.entity.Funcionario;
import com.example.demo.reports.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FuncionarioHtmlController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/funcionarios")
    public String listarFuncionarios(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        // Recupera os funcionários paginados
        Page<Funcionario> funcionariosPage = dashboardService.getFuncionariosPaginados(PageRequest.of(page, size));

        // Adiciona a página completa ao modelo
        model.addAttribute("page", funcionariosPage);
        model.addAttribute("funcionarios", funcionariosPage.getContent());
        model.addAttribute("pageNumber", funcionariosPage.getNumber());
        model.addAttribute("lastPage", funcionariosPage.isLast());

        return "funcionarios"; // Nome do arquivo HTML na pasta templates
    }
}
