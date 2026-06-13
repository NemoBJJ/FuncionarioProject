package com.example.demo.controller;

import com.example.demo.entity.Funcionario;
import com.example.demo.entity.RegistroPonto;
import com.example.demo.model.FuncionarioSalarioDTO;
import com.example.demo.reports.DashboardService;
import com.example.demo.repository.RegistroPontoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = "http://localhost:3001")
public class FuncionarioController {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private RegistroPontoRepository registroPontoRepository;

    @GetMapping
    public Page<Funcionario> listarTodosFuncionariosJson(Pageable pageable) {
        return dashboardService.getFuncionariosPaginados(pageable);
    }

    @GetMapping("/salarios-json")
    public Page<FuncionarioSalarioDTO> listarFuncionariosComSalarioJson(Pageable pageable) {
        return dashboardService.getFuncionariosComSalario(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> buscarFuncionarioPorId(@PathVariable Long id) {
        return dashboardService.buscarFuncionarioPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/face")
    public ResponseEntity<?> cadastrarFace(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        try {
            Funcionario func = dashboardService.buscarFuncionarioPorId(id)
                    .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

            Object descriptor = body.get("descriptor");
            ObjectMapper mapper = new ObjectMapper();
            String descriptorJson = mapper.writeValueAsString(descriptor);

            func.setFaceDescriptor(descriptorJson);
            dashboardService.salvarFuncionario(func);

            System.out.println("✅ Face cadastrada para: " + func.getNome());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Face cadastrada com sucesso");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/reconhecer")
    public ResponseEntity<?> reconhecerFace(@RequestBody Map<String, Object> body) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Double> descriptorRecebido = mapper.convertValue(body.get("descriptor"),
                new com.fasterxml.jackson.core.type.TypeReference<List<Double>>() {});

            List<Funcionario> funcionarios = dashboardService.listarTodosFuncionarios();

            for (Funcionario func : funcionarios) {
                if (func.getFaceDescriptor() != null && !func.getFaceDescriptor().isEmpty()) {
                    List<Double> descriptorSalvo = mapper.readValue(func.getFaceDescriptor(),
                        new com.fasterxml.jackson.core.type.TypeReference<List<Double>>() {});

                    double similaridade = calcularSimilaridade(descriptorRecebido, descriptorSalvo);
                    System.out.println("Similaridade com " + func.getNome() + ": " + similaridade);

                    if (similaridade > 0.6) {
                        // REGISTRA O PONTO NO BANCO
                        RegistroPonto registro = new RegistroPonto();
                        registro.setFuncionarioId(func.getId());
                        registro.setFuncionarioNome(func.getNome());
                        registro.setDataHora(LocalDateTime.now());
                        registro.setTipo("ENTRADA");
                        registro.setSimilaridade(similaridade);
                        registroPontoRepository.save(registro);
                        
                        System.out.println("✅ Ponto registrado para: " + func.getNome() + " às " + registro.getDataHora());

                        Map<String, Object> response = new HashMap<>();
                        response.put("success", true);
                        response.put("nome", func.getNome());
                        response.put("cargo", func.getCargo());
                        response.put("mensagem", "✅ Ponto registrado com sucesso!");
                        return ResponseEntity.ok(response);
                    }
                }
            }

            return ResponseEntity.status(401).body(Map.of("error", "Face não reconhecida"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/ponto/historico")
    public ResponseEntity<List<RegistroPonto>> listarHistoricoPonto() {
        return ResponseEntity.ok(registroPontoRepository.findAllByOrderByDataHoraDesc());
    }

    private double calcularSimilaridade(List<Double> desc1, List<Double> desc2) {
        double soma = 0.0;
        for (int i = 0; i < desc1.size(); i++) {
            soma += Math.pow(desc1.get(i) - desc2.get(i), 2);
        }
        return 1.0 / (1.0 + Math.sqrt(soma));
    }
}