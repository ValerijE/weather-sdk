package com.evv.kameleoonweathersdk.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

@CacheConfig(cacheManager = "inMemoryCacheManager")
@RequiredArgsConstructor
@Slf4j
public class OpenweatherRestClient {

    private final RestClient restClient;

    @Cacheable(value = "cityWeather", key = "#city")
    public ResponseEntity<WeatherIncomingPayload> getWeather(String apiKey, String city) {
        log.info("Rest client is going to get weather for city: {} for api-key: {}", city, apiKey);

        return restClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("appid", apiKey)
                        .queryParam("q", city)
                        .build())
                .retrieve()
                .toEntity(WeatherIncomingPayload.class);
    }
}
