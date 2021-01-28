package com.example.myweatherapp.services;

import com.example.myweatherapp.models.CurrentWeather;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CurrentWeatherData {
    @GET("weather")
    Call<CurrentWeather> getCurrentWeather(@Query("q") String cityName, @Query("appid") String appId, @Query("units") String units);
}
