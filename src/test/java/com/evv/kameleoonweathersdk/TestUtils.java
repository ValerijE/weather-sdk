package com.evv.kameleoonweathersdk;

public final class TestUtils {

    private TestUtils() {
    }

    public static final String DUMMY_API_KEY = "90fr713d9515a60fc74b2edb1c67cd2c";

    public static final String DUMMY_CITY = "Moscow";

    //language=json
    public static final String NORMAL_REQUEST_PAYLOAD = """
            {
                "appid": "90fr713d9515a60fc74b2edb1c67cd2c",
                "city": "Moscow"
            }
            """;

    //language=json
    public static final String NONEXISTENT_CITY_REQUEST_PAYLOAD = """
            {
                "appid": "90fr713d9515a60fc74b2edb1c67cd2c",
                "city": "Dummycity"
            }
            """;

    //language=json
    public static final String BAD_CITY_REQUEST_PAYLOAD = """
            {
                "appid": "90fr713d9515a60fc74b2edb1c67cd2c",
                "city": " "
            }
            """;

    //language=json
    public static final String BAD_KEY_REQUEST_PAYLOAD = """
            {
                "appid": "0fr713d9515a60fc74b2edb1c67cd2c",
                "city": "Moscow"
            }
            """;

    //language=json
    public static final String NORMAL_WEATHER_INCOMING_PAYLOAD = """
            {
                "coord": {
                    "lon": 37.6156,
                    "lat": 55.7522
                },
                "weather": [
                    {
                        "id": 804,
                        "main": "Clouds",
                        "description": "overcast clouds",
                        "icon": "04n"
                    }
                ],
                "base": "stations",
                "main": {
                    "temp": 269.62,
                    "feels_like": 265.79,
                    "temp_min": 268.39,
                    "temp_max": 269.63,
                    "pressure": 1039,
                    "humidity": 53,
                    "sea_level": 1039,
                    "grnd_level": 1019
                },
                "visibility": 10000,
                "wind": {
                    "speed": 2.64,
                    "deg": 257,
                    "gust": 7.66
                },
                "clouds": {
                    "all": 98
                },
                "dt": 1740153138,
                "sys": {
                    "type": 2,
                    "id": 2095214,
                    "country": "RU",
                    "sunrise": 1740112829,
                    "sunset": 1740149170
                },
                "timezone": 10800,
                "id": 524901,
                "name": "Moscow",
                "cod": 200
            }
            """;

    //language=json
    public static final String NOT_FOUND_INCOMING_PAYLOAD = """
            {
              "cod": "404",
              "message": "city not found"
            }
            """.replaceAll("\\s{2,}|\\n", "");

    //language=json
    public static final String NORMAL_WEATHER_SDK_PAYLOAD = """
            {
                "weather": {
                    "main": "Clouds",
                    "description": "overcast clouds"
                },
                "temperature": {
                    "temp": "269.62",
                    "feels_like": "265.79"
                },
                "visibility": "10000",
                "wind": {
                    "speed": "2.64"
                },
                "datetime": "1740153138",
                "sys": {
                    "sunrise": "1740112829",
                    "sunset": "1740149170"
                },
                "timezone": "10800",
                "name": "Moscow"
            }
            """;

    //language=json
    public static final String NOT_FOUND_SDK_PAYLOAD = """
            {
                "type": "about:blank",
                "title": "Not Found",
                "status": 404,
                "detail": "404 Not Found: \\"{\\"cod\\": \\"404\\",\\"message\\": \\"city not found\\"}\\"",
                "instance": "/api/v1/weather"
            }
            """;

    //language=json
    public static final String BAD_KEY_SDK_PAYLOAD = """
            {
              "type": "about:blank",
              "title": "Validation Error",
              "status": 400,
              "detail": "OpenWeather API Key must be 32 characters long",
              "instance": "/api/v1/weather"
            }
            """;

    //language=json
    public static final String BAD_CITY_SDK_PAYLOAD = """
            {
              "type": "about:blank",
              "title": "Validation Error",
              "status": 400,
              "detail": "City name must not be empty and must be at least 1 character long",
              "instance": "/api/v1/weather"
            }
            """;

}
