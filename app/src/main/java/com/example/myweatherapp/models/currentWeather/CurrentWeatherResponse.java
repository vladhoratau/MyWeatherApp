package com.example.myweatherapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CurrentWeather {
    @SerializedName("coord")
    @Expose
    private Coordinates coordinates;

    @SerializedName("name")
    @Expose
    private String cityName;

    @SerializedName("weather.description")
    @Expose
    private String description;

    @SerializedName("main.temp")
    @Expose
    private Double temp;


    public CurrentWeather(Coordinates coordinates, String cityName, String description, Double temp) {
        this.coordinates = coordinates;
        this.cityName = cityName;
        this.description = description;
        this.temp = temp;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }


    public String getCityName() {
        return cityName;
    }


    public String getDescription() {
        return description;
    }


    public Double getTemp() {
        return temp;
    }


}
