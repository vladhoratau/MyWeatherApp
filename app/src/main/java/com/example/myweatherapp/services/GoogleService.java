package com.example.myweatherapp.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GoogleService {
    @GET("maps/api/geocode/json")
    Call<Object> getLatAndLong(@Query("city") String address, @Query("sensor") String sensor);
}
