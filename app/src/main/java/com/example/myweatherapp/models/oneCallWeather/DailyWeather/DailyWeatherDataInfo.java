package com.example.myweatherapp.models.oneCallWeather.DailyWeather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyWeatherDataInfo {

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("icon")
    @Expose
    private String icon;

    public DailyWeatherDataInfo(String description, String icon) {
        this.description = description;
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
