package com.evv.kameleoonweathersdk.controller;

import com.evv.kameleoonweathersdk.client.OpenweatherRestClient;
import com.evv.kameleoonweathersdk.client.WeatherIncomingPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/weather")
@RequiredArgsConstructor
public class WeatherRestController {

    private final OpenweatherRestClient restClient;

    @GetMapping
    public ResponseEntity<WeatherSdkPayload> getWeather(
            @Validated @RequestBody RequestPayload requestPayload) {


        ResponseEntity<WeatherIncomingPayload> weather =
                restClient.getWeather(requestPayload.getApiKey(), requestPayload.getCity());

        return ResponseEntity.ok().body(new WeatherSdkPayload(weather.getBody()));
    }
}
