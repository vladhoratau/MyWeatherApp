package com.example.myweatherapp.models.currentWeather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Country {

    @SerializedName("country")
    @Expose
    private String countryName;

    public Country(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }
}
