package com.example.myweatherapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myweatherapp.models.currentWeather.CurrentWeatherResponse;
import com.example.myweatherapp.repositories.CurrentWeatherRepository;

public class GetCurrentWeatherViewModel extends ViewModel {
    private CurrentWeatherRepository currentWeatherRepository;
    private LiveData<CurrentWeatherResponse> currentWeatherLiveData;

    public void init() {
        currentWeatherRepository = new CurrentWeatherRepository();
        currentWeatherLiveData = currentWeatherRepository.getCurrentWeatherLiveData();
    }

    public void getCurrentWeather(String cityName, String unit) {
        currentWeatherRepository.getCurrentWeather(cityName, unit);
    }

    public LiveData<CurrentWeatherResponse> getCurrentWeatherLiveData() {
        return currentWeatherLiveData;
    }
}
