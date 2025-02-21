package com.evv.kameleoonweathersdk.config;

import com.evv.kameleoonweathersdk.client.OpenweatherRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientConfig {

    @Bean
    OpenweatherRestClient openweatherRestClient(@Value("${openweather.api.uri:https://api.openweathermap.org/data/2.5/weather}") String uri) {
        return new OpenweatherRestClient(RestClient.builder()
                .baseUrl(uri)
                .build());
    }
}