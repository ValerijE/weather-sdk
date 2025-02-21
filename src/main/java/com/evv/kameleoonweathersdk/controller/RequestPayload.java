package com.evv.kameleoonweathersdk.controller;

import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
@NotNull(message = "Empty request body received")
public class RequestPayload {

    @Size(min = 32, max = 32, message = "OpenWeather API Key must be 32 characters long")
    @JsonSetter("appid")
    String apiKey;

    @NotBlank(message = "City name must not be empty and must be at least 1 character long")
    String city;
}
