package com.example.myweatherapp.models.oneCallWeather.DailyWeather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyWeatherTempInfo {

    @SerializedName("day")
    @Expose
    private Double day;

    @SerializedName("night")
    @Expose
    private Double night;

    public DailyWeatherTempInfo(Double day, Double night) {
        this.day = day;
        this.night = night;
    }

    public Double getDay() {
        return day;
    }

    public Double getNight() {
        return night;
    }
}
