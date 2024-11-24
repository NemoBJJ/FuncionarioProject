package com.example.demo.reports;

import com.example.demo.model.RelatorioCargo;
import com.example.demo.model.RelatorioDepartamento;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Service
public class CSVExporter {

    public void exportRelatoriosPorCargo(List<RelatorioCargo> relatorios, PrintWriter writer) throws IOException {
        try (CSVWriter csvWriter = new CSVWriter(writer)) {
            // Header
            csvWriter.writeNext(new String[]{"Cargo", "Quantidade de Funcionários"});
            // Dados
            for (RelatorioCargo relatorio : relatorios) {
                csvWriter.writeNext(new String[]{
                        relatorio.getCargo(),
                        String.valueOf(relatorio.getQuantidadeFuncionarios())
                });
            }
        }
    }

    public void exportRelatoriosPorDepartamento(List<RelatorioDepartamento> relatorios, PrintWriter writer) throws IOException {
        try (CSVWriter csvWriter = new CSVWriter(writer)) {
            // Header
            csvWriter.writeNext(new String[]{"Departamento", "Total de Salários"});
            // Dados
            for (RelatorioDepartamento relatorio : relatorios) {
                csvWriter.writeNext(new String[]{
                        relatorio.getDepartamento(),
                        String.valueOf(relatorio.getSomaSalarios())
                });
            }
        }
    }
}
