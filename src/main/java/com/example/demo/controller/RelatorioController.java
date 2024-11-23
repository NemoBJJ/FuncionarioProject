package com.example.demo.controller;

import com.example.demo.model.RelatorioCargo;
import com.example.demo.model.RelatorioDepartamento;
import com.example.demo.reports.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final RelatorioService relatorioService;
    private final CacheManager cacheManager;

    @Autowired
    public RelatorioController(RelatorioService relatorioService, CacheManager cacheManager) {
        this.relatorioService = relatorioService;
        this.cacheManager = cacheManager;
    }

    @GetMapping("/cargos")
    public List<RelatorioCargo> getRelatoriosPorCargo() {
        return relatorioService.obterRelatoriosPorCargo();
    }

    @GetMapping("/departamentos")
    public List<RelatorioDepartamento> getRelatoriosPorDepartamento() {
        return relatorioService.obterRelatoriosPorDepartamento();
    }

    @GetMapping("/cache/verificar")
    public String verificarCache() {
        StringBuilder resultado = new StringBuilder();

        verificarCacheInterno("relatoriosPorCargo", resultado);
        verificarCacheInterno("relatoriosPorDepartamento", resultado);

        return resultado.toString();
    }

    private void verificarCacheInterno(String cacheName, StringBuilder resultado) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            Object valor = cache.get(cacheName.equals("relatoriosPorCargo") ? "cargo" : "departamento");
            resultado.append("Cache '").append(cacheName).append("' encontrado. Valor em cache: ")
                     .append(valor != null ? valor : "Nulo").append("\n");
        } else {
            resultado.append("Cache '").append(cacheName).append("' não encontrado.\n");
        }
    }
}
