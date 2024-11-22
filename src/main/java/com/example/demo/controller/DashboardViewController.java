package com.example.demo.controller;

import com.example.demo.reports.DashboardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardViewController {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public String showDashboard(Model model) throws Exception {
        // Obtém os dados do serviço
        var dados = dashboardService.getDadosDashboard();

        // Converte os dados para JSON
        String dadosJson = objectMapper.writeValueAsString(dados);

        // Adiciona os dados JSON ao modelo para Thymeleaf
        model.addAttribute("dadosJson", dadosJson);

        return "dashboard"; // Nome do arquivo HTML
    }
}
