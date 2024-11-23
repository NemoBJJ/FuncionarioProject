package com.example.demo.reports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class CacheInspector {

    @Autowired
    private CacheManager cacheManager;

    public void verificarCache(String cacheName, String chave) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            Object valor = cache.get(chave); // Insira a chave correspondente
            System.out.println("Valor em cache para chave " + chave + ": " + valor);
        } else {
            System.out.println("Cache '" + cacheName + "' não encontrado.");
        }
    }
}
