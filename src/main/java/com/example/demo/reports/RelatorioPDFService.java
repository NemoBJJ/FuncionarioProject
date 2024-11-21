package com.example.demo.reports;

import com.example.demo.entity.Funcionario;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.stream.Stream;
import java.util.List;

@Service
public class RelatorioPDFService {

    public byte[] gerarRelatorioPDF(List<Funcionario> funcionarios) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();

            // Título estilizado
            Paragraph title = new Paragraph("Relatório de Funcionários", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n")); // Espaço após o título

            // Configuração da tabela
            PdfPTable table = new PdfPTable(8); // Número de colunas ajustado
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1.5f, 2.5f, 3f, 3f, 3f, 2.5f, 2.5f, 3.5f});

            // Cabeçalhos
            Stream.of("ID", "Nome", "Cargo", "Data de Admissão", "Nível de Experiência", "Salário (R$)", "Bônus (R$)", "Departamento")
                    .forEach(headerTitle -> {
                        PdfPCell header = new PdfPCell();
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setPhrase(new Phrase(headerTitle, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                        header.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(header);
                    });

            // Dados dos funcionários
            for (Funcionario funcionario : funcionarios) {
                // ID
                PdfPCell idCell = new PdfPCell(new Phrase(funcionario.getId().toString()));
                idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(idCell);

                // Nome
                PdfPCell nomeCell = new PdfPCell(new Phrase(funcionario.getNome()));
                nomeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(nomeCell);

                // Cargo
                table.addCell(funcionario.getCargo());

                // Data de Admissão
                table.addCell(funcionario.getDataAdmissao() != null ? funcionario.getDataAdmissao().toString() : "Não informado");

                // Nível de Experiência (centralizado)
                PdfPCell nivelCell = new PdfPCell(new Phrase(funcionario.getNivelExperiencia() != null ? funcionario.getNivelExperiencia() : "Não informado"));
                nivelCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(nivelCell);

                // Salário
                PdfPCell salarioCell = new PdfPCell(new Phrase(funcionario.getSalario() != null ? funcionario.getSalario().toString() : "0.0"));
                salarioCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(salarioCell);

                // Bônus
                PdfPCell bonusCell = new PdfPCell(new Phrase(funcionario.getBonus() != null ? funcionario.getBonus().toString() : "0.0"));
                bonusCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(bonusCell);

                // Departamento
                PdfPCell departamentoCell = new PdfPCell(new Phrase(funcionario.getDepartamento() != null ? funcionario.getDepartamento() : "Não informado"));
                departamentoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(departamentoCell);
            }

            document.add(table);
            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF", e);
        }
    }
}
