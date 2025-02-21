package com.evv.kameleoonweathersdk.client;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Value;

@Value
public class WeatherIncomingPayload {

    Coordinates coord;

    Weather[] weather;

    String base;

    MainInfo main;

    String visibility;

    Wind wind;

    Clouds clouds;

    String dt;

    SystemInfo sys;

    String timezone;

    String id;

    String name;

    String cod;

    /**
     * Field for error message
     */
    String message;


    @Value
    public static class Coordinates {
        String lon;
        String lat;
    }

    @Value
    public static class Weather {
        String id;
        String main;
        String description;
        String icon;
    }

    @Value
    public static class MainInfo {
        String temp;
        @JsonSetter("feels_like")
        String feelsLike;
        String pressure;
        String humidity;
        @JsonSetter("temp_min")
        String tempMin;
        @JsonSetter("temp_max")
        String tempMax;
    }

    @Value
    public static class Wind {
        String speed;
        String deg;
    }

    @Value
    public static class Clouds {
        String all;
    }

    @Value
    public static class SystemInfo {
        String type;
        String id;
        String message;
        String country;
        String sunrise;
        String sunset;
    }

}
