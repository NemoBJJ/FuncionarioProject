package com.example.demo.controller;

import com.example.demo.entity.Funcionario;
import com.example.demo.repository.FuncionarioRepository;
import com.example.demo.reports.RelatorioPDFService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private RelatorioPDFService relatorioPDFService;

    @GetMapping
    public List<Funcionario> listarTodos() {
        // Busca todos os funcionários no banco
        return funcionarioRepository.findAll();
    }

    @PostMapping
    public Funcionario criarFuncionario(@RequestBody Funcionario funcionario) {
        // Salva um novo funcionário no banco
        return funcionarioRepository.save(funcionario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> buscarPorId(@PathVariable Long id) {
        // Busca um funcionário pelo ID
        return funcionarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> atualizarFuncionario(
            @PathVariable Long id, @RequestBody Funcionario funcionarioAtualizado) {
        // Atualiza os dados de um funcionário existente
        return funcionarioRepository.findById(id)
                .map(funcionario -> {
                    funcionario.setNome(funcionarioAtualizado.getNome());
                    funcionario.setCargo(funcionarioAtualizado.getCargo());
                    funcionario.setSalario(funcionarioAtualizado.getSalario());
                    return ResponseEntity.ok(funcionarioRepository.save(funcionario));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFuncionario(@PathVariable Long id) {
        // Deleta um funcionário pelo ID
        if (funcionarioRepository.existsById(id)) {
            funcionarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/relatorio/pdf")
    public ResponseEntity<byte[]> exportarRelatorioPDF() {
        // Gera e exporta o relatório em PDF
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        byte[] pdfBytes = relatorioPDFService.gerarRelatorioPDF(funcionarios);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "relatorio-funcionarios.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}
