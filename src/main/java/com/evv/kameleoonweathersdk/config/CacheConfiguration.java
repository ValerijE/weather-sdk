package com.evv.kameleoonweathersdk.config;

import com.google.common.cache.CacheBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
@EnableConfigurationProperties(ApplicationCacheProperties.class)
public class CacheConfiguration {

    @Bean
    public ConcurrentMapCacheManager inMemoryCacheManager(ApplicationCacheProperties appCacheProperties) {
        var cacheManager = new ConcurrentMapCacheManager() {
            @Override
            protected Cache createConcurrentMapCache(String name) {
                return new ConcurrentMapCache(
                        name,
                        CacheBuilder.newBuilder()
                                .expireAfterWrite(
                                        appCacheProperties
                                                .getCaches()
                                                .get(name)
                                                .getExpiry())
                                .maximumSize(
                                        appCacheProperties
                                                .getCaches()
                                                .get(name)
                                                .getLimit()
                                )
                                .build().asMap(),
                        true
                );
            }
        };
        var cacheNames = appCacheProperties.getCacheNames();
        if (!cacheNames.isEmpty()) {
            cacheManager.setCacheNames(cacheNames);
        }

        return cacheManager;
    }
}
