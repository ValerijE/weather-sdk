package com.evv.kameleoonweathersdk.controller;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.evv.kameleoonweathersdk.TestUtils.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WireMockTest(httpPort = 54321)
@ActiveProfiles("test")
class WeatherRestControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getWeather_NormalFlow_ReturnWeatherSdkPayload() throws Exception {

        // given
        var requestBuilder = MockMvcRequestBuilders.get("/api/v1/weather")
                .contentType(MediaType.APPLICATION_JSON)
                .content(NORMAL_REQUEST_PAYLOAD);

        WireMock.stubFor(WireMock.get(urlPathMatching("/data/2.5/weather"))
                .withQueryParam("appid", equalTo(DUMMY_API_KEY))
                .withQueryParam("q", equalTo(DUMMY_CITY))
        .willReturn(WireMock.okJson(NORMAL_WEATHER_INCOMING_PAYLOAD)));

        // when
        mockMvc.perform(requestBuilder)
        // then
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json(NORMAL_WEATHER_SDK_PAYLOAD)
                );

        WireMock.verify(getRequestedFor(urlPathMatching("/data/2.5/weather")));
    }

    @Test
    void getWeather_CityNotExists_Return404() throws Exception {

        // given
        var requestBuilder = MockMvcRequestBuilders.get("/api/v1/weather")
                .contentType(MediaType.APPLICATION_JSON)
                .content(NONEXISTENT_CITY_REQUEST_PAYLOAD);

        WireMock.stubFor(WireMock.get(urlPathMatching("/data/2.5/weather"))
                .withQueryParam("appid", equalTo("90fr713d9515a60fc74b2edb1c67cd2c"))
                .withQueryParam("q", equalTo("Dummycity"))
                .willReturn(WireMock.status(404).withBody(NOT_FOUND_INCOMING_PAYLOAD)));

        // when
        mockMvc.perform(requestBuilder)
        // then
                .andExpectAll(
                        status().isNotFound(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON),
                        content().json(NOT_FOUND_SDK_PAYLOAD)
                );

        WireMock.verify(getRequestedFor(urlPathMatching("/data/2.5/weather")));
    }

    @Test
    void getWeather_Key31CharacterButNot32_ShouldValidate() throws Exception {

        // given
        var requestBuilder = MockMvcRequestBuilders.get("/api/v1/weather")
                .contentType(MediaType.APPLICATION_JSON)
                .content(BAD_KEY_REQUEST_PAYLOAD);

        // when
        mockMvc.perform(requestBuilder)
        // then
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON),
                        content().json(BAD_KEY_SDK_PAYLOAD)
                );
    }

    @Test
    void getWeather_CityIs1Whitespace_ShouldValidate() throws Exception {

        // given
        var requestBuilder = MockMvcRequestBuilders.get("/api/v1/weather")
                .contentType(MediaType.APPLICATION_JSON)
                .content(BAD_CITY_REQUEST_PAYLOAD);

        // when
        mockMvc.perform(requestBuilder)
        // then
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON),
                        content().json(BAD_CITY_SDK_PAYLOAD)
                );
    }
}