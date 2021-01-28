package com.example.myweatherapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myweatherapp.R;
import com.example.myweatherapp.models.currentWeather.CurrentWeatherResponse;
import com.example.myweatherapp.services.CurrentWeatherDataService;
import com.example.myweatherapp.utils.ApplicationClass;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrentWeatherRepository {
    private CurrentWeatherDataService currentWeatherDataService;
    private MutableLiveData<CurrentWeatherResponse> currentWeatherLiveData;

    public CurrentWeatherRepository() {
        currentWeatherLiveData = new MutableLiveData<>();

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(ApplicationClass.getInstance().getString(R.string.open_weather_root))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
         currentWeatherDataService = retrofit.create(CurrentWeatherDataService.class);
    }

    public void getCurrentWeather(String cityName, String unit){
        currentWeatherDataService.getCurrentWeather(cityName, ApplicationClass.getInstance()
                .getString(R.string.api_key), unit).enqueue(new Callback<CurrentWeatherResponse>() {
            @Override
            public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                if(response.isSuccessful()){
                    currentWeatherLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {
                    currentWeatherLiveData.postValue(null);
                    t.printStackTrace();
            }
        });
    }

    public LiveData<CurrentWeatherResponse> getCurrentWeatherLiveData(){
        return currentWeatherLiveData;
    }
}
