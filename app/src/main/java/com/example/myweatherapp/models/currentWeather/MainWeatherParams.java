package com.example.myweatherapp.models.currentWeather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainWeatherParams {
    @SerializedName("temp")
    @Expose
    private Double temp;

    @SerializedName("feels_like")
    @Expose
    private Double feelsLike;

    public MainWeatherParams(Double temp, Double feelsLike) {
        this.temp = temp;
        this.feelsLike = feelsLike;
    }

    public Double getTemp() {
        return temp;
    }

    public Integer getIntTemp() {
        return getTemp().intValue();
    }

    public Double getFeelsLike() {
        return feelsLike;
    }

    public Integer getIntFeelsLike() {
        return getFeelsLike().intValue();
    }

}
