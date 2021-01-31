package com.example.myweatherapp.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myweatherapp.R;
import com.example.myweatherapp.models.OneCallWeather.DailyWeather.DailyWeatherData;
import com.example.myweatherapp.models.OneCallWeather.DailyWeather.DailyWeatherResponse;
import com.example.myweatherapp.models.currentWeather.Coordinates;
import com.example.myweatherapp.models.currentWeather.CurrentWeatherResponse;
import com.example.myweatherapp.models.OneCallWeather.HourlyWeather.HourlyWeatherData;
import com.example.myweatherapp.models.OneCallWeather.HourlyWeather.HourlyWeatherResponse;
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
    private MutableLiveData<List<DailyWeatherData>> dailyWeatherLiveData;
    private MutableLiveData<List<HourlyWeatherData>> historicalWeatherLive;

    private MutableLiveData<Coordinates> coordinatesLiveData;

    public CurrentWeatherRepository() {
        currentWeatherLiveData = new MutableLiveData<>();
        hourlyWeatherDataLiveData = new MutableLiveData<>();
        coordinatesLiveData = new MutableLiveData<>();
        tomorrowWeatherLiveData = new MutableLiveData<>();
        dailyWeatherLiveData = new MutableLiveData<>();
        historicalWeatherLive = new MutableLiveData<>(new ArrayList<>());
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
                        if (response.isSuccessful()) {
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
                        } else {
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

    public void getDailyWeather(String unit) {
        currentWeatherDataService.getDailyWeather(coordinatesLiveData.getValue().getLat()
                , coordinatesLiveData.getValue().getLon()
                , unit, "current,minutely,hourly,alerts"
                , ApplicationClass.getInstance().getString(R.string.api_key)).enqueue(new Callback<DailyWeatherResponse>() {
            @Override
            public void onResponse(Call<DailyWeatherResponse> call, Response<DailyWeatherResponse> response) {
                if (response.isSuccessful()) {
                    List<DailyWeatherData> recievedWeatherData = response.body().getDailyWeatherData();
                    List<DailyWeatherData> dailyWeatherData = new ArrayList<>(recievedWeatherData.subList(0, 5));
                    dailyWeatherLiveData.postValue(dailyWeatherData);
                } else {
                    dailyWeatherLiveData.postValue(new ArrayList<DailyWeatherData>());
                }
            }

            @Override
            public void onFailure(Call<DailyWeatherResponse> call, Throwable t) {
                dailyWeatherLiveData.postValue(new ArrayList<DailyWeatherData>());
                t.printStackTrace();
            }
        });
    }

    public void getHistoricalDataForLast5Days(String unit) {
        List<Long> dts = DateUtil.getLastDaysUnix(5);
        for(int i = 0; i < dts.size(); i++) {
            getHistoricalWeatherForDay(unit, dts.get(i));
        }
    }

    private void getHistoricalWeatherForDay(String unit, Long dt) {
        currentWeatherDataService.getHistoricalWeather(coordinatesLiveData.getValue().getLat(),
                coordinatesLiveData.getValue().getLon(),
                dt,
                unit,
                ApplicationClass.getInstance().getString(R.string.api_key))
                .enqueue(new Callback<HourlyWeatherResponse>() {
            @Override
            public void onResponse(Call<HourlyWeatherResponse> call, Response<HourlyWeatherResponse> response) {
                if(response.isSuccessful()) {
                    List<HourlyWeatherData> receivedWeather = response.body().getHourlyWeatherDataList();
                    List<HourlyWeatherData> newHistoricalWeather = historicalWeatherLive.getValue();
                    newHistoricalWeather.add(receivedWeather.get(0));
                    historicalWeatherLive.postValue(newHistoricalWeather);
                }
            }

            @Override
            public void onFailure(Call<HourlyWeatherResponse> call, Throwable t) {
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

    public MutableLiveData<List<HourlyWeatherData>> getHistoricalWeatherLive() {
        return historicalWeatherLive;
    }

    public MutableLiveData<Coordinates> getCoordinatesLiveData() {
        return coordinatesLiveData;
    }

    public MutableLiveData<List<HourlyWeatherData>> getTomorrowWeatherLiveData() {
        return tomorrowWeatherLiveData;
    }

    public MutableLiveData<List<DailyWeatherData>> getDailyWeatherLiveData() {
        return dailyWeatherLiveData;
    }


}
