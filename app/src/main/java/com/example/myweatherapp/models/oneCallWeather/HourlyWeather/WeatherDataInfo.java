package com.example.myweatherapp.models.oneCallWeather.HourlyWeather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherDataInfo {

    @SerializedName("description")
    @Expose
    String description;

    @SerializedName("icon")
    @Expose
    String icon;

    public WeatherDataInfo(String description, String icon) {
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
