package com.example.myweatherapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myweatherapp.R;
import com.example.myweatherapp.models.currentWeather.Coordinates;
import com.example.myweatherapp.models.currentWeather.CurrentWeatherResponse;
import com.example.myweatherapp.models.hourlyWeather.HourlyWeatherData;
import com.example.myweatherapp.models.hourlyWeather.HourlyWeatherResponse;
import com.example.myweatherapp.services.CurrentWeatherDataService;
import com.example.myweatherapp.utils.ApplicationClass;
import com.example.myweatherapp.utils.DateUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrentWeatherRepository {
    private CurrentWeatherDataService currentWeatherDataService;
    private MutableLiveData<CurrentWeatherResponse> currentWeatherLiveData;
    private MutableLiveData<List<HourlyWeatherData>> hourlyWeatherDataLiveData;
    private MutableLiveData<List<HourlyWeatherData>> tomorrowWeatherLiveData;
    private MutableLiveData<Coordinates> coordinatesLiveData;

    public CurrentWeatherRepository() {
        currentWeatherLiveData = new MutableLiveData<>();
        hourlyWeatherDataLiveData = new MutableLiveData<>();
        coordinatesLiveData = new MutableLiveData<>();
        tomorrowWeatherLiveData = new MutableLiveData<>();

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(ApplicationClass.getInstance().getString(R.string.open_weather_root))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        currentWeatherDataService = retrofit.create(CurrentWeatherDataService.class);
    }

    public void getCurrentWeather(String cityName, String unit) {
        currentWeatherDataService.getCurrentWeather(cityName, ApplicationClass.getInstance()
                .getString(R.string.api_key), unit).enqueue(new Callback<CurrentWeatherResponse>() {
            @Override
            public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                if (response.isSuccessful()) {
                    currentWeatherLiveData.postValue(response.body());
                    coordinatesLiveData.postValue(response.body().getCoordinates());
                } else {
                    currentWeatherLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {
                currentWeatherLiveData.postValue(null);
                t.printStackTrace();
            }
        });
    }

    public void getHourlyWeather(String unit) {
        currentWeatherDataService
            .getHourlyWeather(coordinatesLiveData.getValue().getLat(),
                    coordinatesLiveData.getValue().getLon(),
                    unit,
                    "current,minutely,daily,alerts",
                    ApplicationClass.getInstance().getString(R.string.api_key))
            .enqueue(new Callback<HourlyWeatherResponse>() {
                @Override
                public void onResponse(Call<HourlyWeatherResponse> call, Response<HourlyWeatherResponse> response) {
                    if(response.isSuccessful()) {
                        List<HourlyWeatherData> todayWeather = new ArrayList<>();
                        List<HourlyWeatherData> receivedData = response.body().getHourlyWeatherDataList();
                        int i;
                        for (i = 0; i < receivedData.size(); i++) {
                            if (i != 0 && DateUtil.getHourFromUnix(receivedData.get(i).getDt()) == 0) {
                                break;
                            }
                            todayWeather.add(receivedData.get(i));
                        }

                        List<HourlyWeatherData> tomorrowWeather = new ArrayList<>(receivedData.subList(i, i + 24));

                        hourlyWeatherDataLiveData.postValue(todayWeather);
                        tomorrowWeatherLiveData.postValue(tomorrowWeather);
                    }
                    else {
                        hourlyWeatherDataLiveData.postValue(new ArrayList<HourlyWeatherData>());
                    }
                }

                @Override
                public void onFailure(Call<HourlyWeatherResponse> call, Throwable t) {
                    hourlyWeatherDataLiveData.postValue(new ArrayList<HourlyWeatherData>());
                    t.printStackTrace();
                }
            });
    }

    public LiveData<CurrentWeatherResponse> getCurrentWeatherLiveData() {
        return currentWeatherLiveData;
    }

    public MutableLiveData<List<HourlyWeatherData>> getHourlyWeatherDataLiveData() {
        return hourlyWeatherDataLiveData;
    }

    public MutableLiveData<Coordinates> getCoordinatesLiveData() {
        return coordinatesLiveData;
    }

    public MutableLiveData<List<HourlyWeatherData>> getTomorrowWeatherLiveData() {
        return tomorrowWeatherLiveData;
    }
}
