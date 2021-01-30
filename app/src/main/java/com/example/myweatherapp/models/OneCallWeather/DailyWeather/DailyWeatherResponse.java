package com.example.myweatherapp.models.OneCallWeather.DailyWeather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyWeatherResponse {

    @SerializedName("lat")
    @Expose
    private Double lat;

    @SerializedName("lon")
    @Expose
    private Double lon;

    @SerializedName("daily")
    @Expose
    private List<DailyWeatherData> dailyWeatherData;

    public DailyWeatherResponse(Double lat, Double lon, List<DailyWeatherData> dailyWeatherData) {
        this.lat = lat;
        this.lon = lon;
        this.dailyWeatherData = dailyWeatherData;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public List<DailyWeatherData> getDailyWeatherData() {
        return dailyWeatherData;
    }
}
