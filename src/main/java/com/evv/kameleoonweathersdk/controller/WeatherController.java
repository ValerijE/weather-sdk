package com.evv.kameleoonweathersdk.controller;

import com.evv.kameleoonweathersdk.client.OpenweatherRestClient;
import com.evv.kameleoonweathersdk.client.WeatherIncomingPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("weather")
@RequiredArgsConstructor
public class WeatherController {

    private final OpenweatherRestClient restClient;

    @GetMapping
    public String showStartPage(Model model,
                                @ModelAttribute RequestPayload payload) {
        model.addAttribute("requestPayload", payload);
        return "request_page";
    }

    @PostMapping
    public String processWeather(Model model,
                                 @ModelAttribute @Validated RequestPayload payload,
                                 BindingResult bindingResult) {

        model.addAttribute("requestPayload", payload);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "request_page";
        }

        List<ObjectError> errors = new ArrayList<>();

        ResponseEntity<WeatherIncomingPayload> weather;
        try {
            weather = restClient.getWeather(payload.getApiKey(), payload.getCity());
        } catch (Exception e) {
            errors.add(new ObjectError("OpenWeather error", e.getMessage()));
            model.addAttribute("errors", errors);
            return "request_page";
        }

        if (weather.getStatusCode().is2xxSuccessful() && weather.getBody() != null) {
            WeatherSdkPayload weatherToShow = new WeatherSdkPayload(weather.getBody());
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonToShow = objectMapper.writerWithDefaultPrettyPrinter()
                        .writeValueAsString(weatherToShow);
                model.addAttribute("weather", jsonToShow);
            } catch (JsonProcessingException e) {
                errors.add(new ObjectError("Json Conversion",
                        "An error occurred while parsing JSON inside SDK"));
            }
        } else if (!weather.getStatusCode().is2xxSuccessful()) {
            errors.add(new ObjectError("OpenWeather error",
                    weather.getStatusCode() +
                            Objects.requireNonNull(weather.getBody()).getMessage()));
        } else if (weather.getBody() == null) {
            errors.add(new ObjectError("OpenWeather error",
                    "OpenWeather send an empty body"));
        }

        if (!errors.isEmpty())
            model.addAttribute("errors", errors);

        return "request_page";
    }
}
