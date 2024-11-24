package com.example.demo.controller;

import com.example.demo.model.RelatorioCargo;
import com.example.demo.model.RelatorioDepartamento;
import com.example.demo.reports.CSVExporter;
import com.example.demo.reports.RelatorioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/exportacao/relatorios")
public class ExportacaoRelatorioController {

    private final RelatorioService relatorioService;
    private final CSVExporter csvExporter;

    public ExportacaoRelatorioController(RelatorioService relatorioService, CSVExporter csvExporter) {
        this.relatorioService = relatorioService;
        this.csvExporter = csvExporter;
    }

    @GetMapping("/cargos/csv")
    public void exportRelatoriosPorCargoCSV(HttpServletResponse response) throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm"));
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"relatorios_cargos_" + timestamp + ".csv\"");

        List<RelatorioCargo> relatorios = relatorioService.obterRelatoriosPorCargo();
        csvExporter.exportRelatoriosPorCargo(relatorios, response.getWriter());
    }


    @GetMapping("/departamentos/csv")
    public void exportRelatoriosPorDepartamentoCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"relatorios_departamentos.csv\"");

        List<RelatorioDepartamento> relatorios = relatorioService.obterRelatoriosPorDepartamento();
        csvExporter.exportRelatoriosPorDepartamento(relatorios, response.getWriter());
    }
}
