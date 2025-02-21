package com.evv.kameleoonweathersdk.controller;

import com.evv.kameleoonweathersdk.client.WeatherIncomingPayload;
import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Value;

@Value
public class WeatherSdkPayload {

    Weather weather;

    Temperature temperature;

    String visibility;

    Wind wind;

    String datetime;

    SystemInfo sys;

    String timezone;

    String name;

    public WeatherSdkPayload(WeatherIncomingPayload wip) {

        this.weather = new Weather(
                wip.getWeather()[0].getMain(),
                wip.getWeather()[0].getDescription()
        );

        this.temperature = new Temperature(
                wip.getMain().getTemp(),
                wip.getMain().getFeelsLike()
        );

        this.visibility = wip.getVisibility();

        this.wind = new Wind(wip.getWind().getSpeed());

        this.datetime = wip.getDt();

        this.sys = new SystemInfo(
                wip.getSys().getSunrise(),
                wip.getSys().getSunset()
        );

        this.timezone = wip.getTimezone();

        this.name = wip.getName();
    }

    @Value
    private static class Weather {
        String main;
        String description;
    }

    @Value
    private static class Temperature {
        String temp;
        String feelsLike;

        @JsonGetter("feels_like")
        public String getFeelsLike() {
            return feelsLike;
        }
    }

    @Value
    private static class Wind {
        String speed;
    }

    @Value
    private static class SystemInfo {
        String sunrise;
        String sunset;
    }

}
