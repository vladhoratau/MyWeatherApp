package com.example.myweatherapp.services;

import com.example.myweatherapp.models.oneCallWeather.DailyWeather.DailyWeatherResponse;
import com.example.myweatherapp.models.currentWeather.CurrentWeatherResponse;
import com.example.myweatherapp.models.oneCallWeather.HourlyWeather.HourlyWeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CurrentWeatherDataService {
    @GET("weather")
    Call<CurrentWeatherResponse> getCurrentWeather(@Query("q") String cityName, @Query("appid") String appId, @Query("units") String units);

    @GET("onecall")
    Call<HourlyWeatherResponse> getHourlyWeather(@Query("lat") Double lat, @Query("lon") Double lon, @Query("units") String unit, @Query("exclude") String exclude, @Query("appid") String appId);

    @GET("onecall")
    Call<DailyWeatherResponse> getDailyWeather(@Query("lat") Double lat, @Query("lon") Double lon, @Query("units") String unit, @Query("exclude") String exclude, @Query("appid") String appId);

    @GET("onecall/timemachine")
    Call<HourlyWeatherResponse> getHistoricalWeather(@Query("lat") Double lat, @Query("lon") Double lon, @Query("dt") Long dt, @Query("units") String unit, @Query("appid") String appId);
}
