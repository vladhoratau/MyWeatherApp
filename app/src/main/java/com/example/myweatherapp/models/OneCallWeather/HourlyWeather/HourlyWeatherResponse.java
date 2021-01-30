package com.example.myweatherapp.models.OneCallWeather.HourlyWeather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HourlyWeatherResponse {

    @SerializedName("lat")
    @Expose
    Double lat;

    @SerializedName("lon")
    @Expose
    Double lon;

    @SerializedName("hourly")
    @Expose
    List<HourlyWeatherData> hourlyWeatherDataList;

    public HourlyWeatherResponse(Double lat, Double lon, List<HourlyWeatherData> hourlyWeatherDataList) {
        this.lat = lat;
        this.lon = lon;
        this.hourlyWeatherDataList = hourlyWeatherDataList;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public List<HourlyWeatherData> getHourlyWeatherDataList() {
        return hourlyWeatherDataList;
    }
}
