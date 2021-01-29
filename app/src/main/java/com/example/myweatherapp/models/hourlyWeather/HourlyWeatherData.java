package com.example.myweatherapp.models.hourlyWeather;

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
    List<HourlyWeatherDataInfo> hourlyWeatherDataInfoList;

    public HourlyWeatherData(Long dt, Double temp, List<HourlyWeatherDataInfo> hourlyWeatherDataInfoList) {
        this.dt = dt;
        this.temp = temp;
        this.hourlyWeatherDataInfoList = hourlyWeatherDataInfoList;
    }

    public Long getDt() {
        return dt;
    }

    public Double getTemp() {
        return temp;
    }

    public List<HourlyWeatherDataInfo> getHourlyWeatherDataInfoList() {
        return hourlyWeatherDataInfoList;
    }
}
