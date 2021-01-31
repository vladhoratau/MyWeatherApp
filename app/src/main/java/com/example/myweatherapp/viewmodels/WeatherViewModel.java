package com.example.myweatherapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myweatherapp.models.OneCallWeather.DailyWeather.DailyWeatherData;
import com.example.myweatherapp.models.currentWeather.Coordinates;
import com.example.myweatherapp.models.currentWeather.CurrentWeatherResponse;
import com.example.myweatherapp.models.OneCallWeather.HourlyWeather.HourlyWeatherData;
import com.example.myweatherapp.repositories.CurrentWeatherRepository;

import java.util.List;

public class WeatherViewModel extends ViewModel {
    private CurrentWeatherRepository currentWeatherRepository;
    private LiveData<CurrentWeatherResponse> currentWeatherLiveData;
    private LiveData<List<HourlyWeatherData>> hourlyWeatherDataLiveData;
    private LiveData<List<HourlyWeatherData>> tomorrowWeatherDataLiveData;
    private LiveData<List<DailyWeatherData>> dailyWeatherDataLiveData;
    private LiveData<List<HourlyWeatherData>> historicalWeatherDataLiveData;
    private LiveData<Coordinates> coordinatesLiveData;

    public void init() {
        currentWeatherRepository = new CurrentWeatherRepository();
        currentWeatherLiveData = currentWeatherRepository.getCurrentWeatherLiveData();
        hourlyWeatherDataLiveData = currentWeatherRepository.getHourlyWeatherDataLiveData();
        coordinatesLiveData = currentWeatherRepository.getCoordinatesLiveData();
        tomorrowWeatherDataLiveData = currentWeatherRepository.getTomorrowWeatherLiveData();
        dailyWeatherDataLiveData = currentWeatherRepository.getDailyWeatherLiveData();
        historicalWeatherDataLiveData = currentWeatherRepository.getHistoricalWeatherLive();
    }

    public void getCurrentWeather(String cityName, String unit) {
        currentWeatherRepository.getCurrentWeather(cityName, unit);
    }

    public void getHourlyWeather(String unit) {
        currentWeatherRepository.getHourlyWeather(unit);
    }

    public void getDailyWeather(String unit) {
        currentWeatherRepository.getDailyWeather(unit);
    }

    public void getHistoricalWeather (String unit) {
        currentWeatherRepository.getHistoricalDataForLast5Days(unit);
    }

    public LiveData<CurrentWeatherResponse> getCurrentWeatherLiveData() {
        return currentWeatherLiveData;
    }


    public LiveData<List<HourlyWeatherData>> getHourlyWeatherLiveData() {
        return hourlyWeatherDataLiveData;
    }

    public LiveData<Coordinates> getCoordinatesLiveData() {
        return coordinatesLiveData;
    }

    public LiveData<List<HourlyWeatherData>> getTomorrowWeatherDataLiveData() {
        return tomorrowWeatherDataLiveData;
    }

    public LiveData<List<DailyWeatherData>> getDailyWeatherDataLiveData() {
        return dailyWeatherDataLiveData;
    }

    public LiveData<List<HourlyWeatherData>> getHistoricalWeatherDataLiveData() {
        return historicalWeatherDataLiveData;
    }
}
