package com.example.myweatherapp.models.currentWeather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class CurrentWeatherResponse {
    @SerializedName("coord")
    @Expose
    private Coordinates coordinates;

    @SerializedName("name")
    @Expose
    private String cityName;

    @SerializedName("weather")
    @Expose
    private List<Weather> weather;

    @SerializedName("main")
    @Expose
    private MainWeatherParams mainWeatherParams;

    @SerializedName("sys")
    @Expose
    private Country country;

    public CurrentWeatherResponse(Coordinates coordinates, String cityName, List<Weather> weather, MainWeatherParams mainWeatherParams, Country country) {
        this.coordinates = coordinates;
        this.cityName = cityName;
        this.weather = weather;
        this.mainWeatherParams = mainWeatherParams;
        this.country = country;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public String getCityName() {
        return cityName;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public MainWeatherParams getMainWeatherParams() {
        return mainWeatherParams;
    }

    public Country getCountry() {
        return country;
    }


}
