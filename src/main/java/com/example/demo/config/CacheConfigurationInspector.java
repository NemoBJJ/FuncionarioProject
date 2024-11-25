package com.example.demo.config;

import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class CacheConfigurationInspector {

    private final CacheManager cacheManager;

    public CacheConfigurationInspector(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void verificarCache(String cacheName) {
        var cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            System.out.println("Conteúdo do cache '" + cacheName + "': " + cache.getNativeCache());
        } else {
            System.out.println("Cache '" + cacheName + "' não encontrado.");
        }
    }
}
