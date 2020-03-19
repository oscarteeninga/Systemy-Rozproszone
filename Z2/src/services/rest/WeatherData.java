package services.rest;

import services.rest.model.Weather;

public class WeatherData {
    private static Weather weather;

    public static Weather getWeather() {
        return weather;
    }

    public static void setWeather(Weather weather) {
        WeatherData.weather = weather;
    }
}
