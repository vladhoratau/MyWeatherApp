package com.example.myweatherapp.models.OneCallWeather.DailyWeather;

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
    private List<DailyWeatherDataInfo> dailyWeatherDataInfo;

    public DailyWeatherData(Long dt, DailyWeatherTempInfo dailyWeatherTempInfo, List<DailyWeatherDataInfo> dailyWeatherDataInfoList) {
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

    public List<DailyWeatherDataInfo> getDailyWeatherDataInfoList() {
        return dailyWeatherDataInfo;
    }
}
