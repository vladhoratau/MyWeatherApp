package com.example.myweatherapp.models.oneCallWeather.DailyWeather;

import com.example.myweatherapp.models.oneCallWeather.HourlyWeather.WeatherDataInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyWeatherData {

    @SerializedName("dt")
    @Expose
    private Long dt;

    @SerializedName("temp")
    @Expose
    private DailyWeatherTempInfo dailyWeatherTempInfo;

    @SerializedName("weather")
    @Expose
    private List<WeatherDataInfo> dailyWeatherDataInfo;

    public DailyWeatherData(Long dt, DailyWeatherTempInfo dailyWeatherTempInfo, List<WeatherDataInfo> dailyWeatherDataInfoList) {
        this.dt = dt;
        this.dailyWeatherTempInfo = dailyWeatherTempInfo;
        this.dailyWeatherDataInfo = dailyWeatherDataInfoList;
    }

    public Long getDt() {
        return dt;
    }

    public DailyWeatherTempInfo getTemp() {
        return dailyWeatherTempInfo;
    }

    public List<WeatherDataInfo> getDailyWeatherDataInfoList() {
        return dailyWeatherDataInfo;
    }
}
