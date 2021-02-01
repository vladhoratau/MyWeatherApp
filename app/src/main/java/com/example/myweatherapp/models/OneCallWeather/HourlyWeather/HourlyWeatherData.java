package com.example.myweatherapp.models.OneCallWeather.HourlyWeather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HourlyWeatherData {

    @SerializedName("dt")
    @Expose
    Long dt;

    @SerializedName("temp")
    @Expose
    Double temp;

    @SerializedName("weather")
    @Expose
    List<WeatherDataInfo> weatherDataInfoList;

    public HourlyWeatherData(Long dt, Double temp, List<WeatherDataInfo> weatherDataInfoList) {
        this.dt = dt;
        this.temp = temp;
        this.weatherDataInfoList = weatherDataInfoList;
    }

    public Long getDt() {
        return dt;
    }

    public Double getTemp() {
        return temp;
    }

    public List<WeatherDataInfo> getWeatherDataInfoList() {
        return weatherDataInfoList;
    }
}
