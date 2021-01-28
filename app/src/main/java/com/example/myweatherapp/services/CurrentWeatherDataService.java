package com.example.myweatherapp.services;

import com.example.myweatherapp.models.currentWeather.CurrentWeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CurrentWeatherDataService {
    @GET("weather")
    Call<CurrentWeatherResponse> getCurrentWeather(@Query("q") String cityName, @Query("appid") String appId, @Query("units") String units);
}
