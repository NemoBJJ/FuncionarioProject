package com.example.demo.reports;

import com.example.demo.entity.Funcionario;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class RelatorioPDFService {

    public byte[] gerarRelatorioPDF(List<Funcionario> funcionarios) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();
            document.add(new Paragraph("Relatório de Funcionários\n\n"));

            PdfPTable table = new PdfPTable(8); // Número de colunas ajustado para os campos

            // Cabeçalhos
            table.addCell("ID");
            table.addCell("Nome");
            table.addCell("Cargo");
            table.addCell("Data de Admissão");
            table.addCell("Nível de Experiência");
            table.addCell("Salário");
            table.addCell("Bônus");
            table.addCell("Departamento");

            // Dados dos funcionários
            for (Funcionario funcionario : funcionarios) {
                table.addCell(funcionario.getId().toString());
                table.addCell(funcionario.getNome());
                table.addCell(funcionario.getCargo());
                table.addCell(funcionario.getDataAdmissao() != null ? funcionario.getDataAdmissao().toString() : "Não informado");
                table.addCell(funcionario.getNivelExperiencia() != null ? funcionario.getNivelExperiencia() : "Não informado");
                table.addCell(funcionario.getSalario() != null ? funcionario.getSalario().toString() : "0.0");
                table.addCell(funcionario.getBonus() != null ? funcionario.getBonus().toString() : "0.0");
                table.addCell(funcionario.getDepartamento() != null ? funcionario.getDepartamento() : "Não informado");
            }

            document.add(table);
            document.close();

            return out.toByteArray();
        } catch (DocumentException e) {
            throw new RuntimeException("Erro ao gerar PDF", e);
        } catch (Exception e) {
            throw new RuntimeException("Erro geral ao gerar PDF", e);
        }
    }
}
