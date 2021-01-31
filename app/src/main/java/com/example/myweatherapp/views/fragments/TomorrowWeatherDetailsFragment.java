package com.example.myweatherapp.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweatherapp.R;
import com.example.myweatherapp.adapters.WeatherAdapter;
import com.example.myweatherapp.models.currentWeather.Coordinates;
import com.example.myweatherapp.models.OneCallWeather.HourlyWeather.HourlyWeatherData;
import com.example.myweatherapp.utils.DateUtil;
import com.example.myweatherapp.viewmodels.WeatherViewModel;
import com.google.android.material.textview.MaterialTextView;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.util.List;

public class TomorrowWeatherDetailsFragment extends Fragment {
    private WeatherViewModel weatherViewModel;
    private WeatherAdapter weatherAdapter;
    private RecyclerView tomorrowHourlyWeather;
    private MaterialTextView cityName, tomorrowDate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tommorow_weather_details, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tomorrowHourlyWeather = view.findViewById(R.id.tomorrowHourlyWeather);
        cityName = view.findViewById(R.id.tomorrowCityName);
        tomorrowDate = view.findViewById(R.id.tomorrowDate);


        cityName.setText(WordUtils.capitalize(getSearchedLocation(),',', '-'));
        tomorrowDate.setText(DateUtil.getTomorrowDate());

        weatherAdapter = new WeatherAdapter();
        tomorrowHourlyWeather.setLayoutManager(new LinearLayoutManager(getContext()));
        tomorrowHourlyWeather.setAdapter(weatherAdapter);

        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        weatherViewModel.init();

        weatherViewModel.getCurrentWeather(getSearchedLocation(), getUnit());

        weatherViewModel.getCoordinatesLiveData().observe(getViewLifecycleOwner(), new Observer<Coordinates>() {
            @Override
            public void onChanged(Coordinates coordinates) {
                weatherViewModel.getHourlyWeather(getUnit());
            }
        });

        weatherViewModel.getTomorrowWeatherDataLiveData().observe(getViewLifecycleOwner(), new Observer<List<HourlyWeatherData>>() {
            @Override
            public void onChanged(List<HourlyWeatherData> hourlyWeatherData) {
                if(hourlyWeatherData != null) {
                    weatherAdapter.setResults(hourlyWeatherData, getUnitMeasure());
                }
            }
        });
    }

    private String getSearchedLocation() {
        return getArguments().getString("searchedLocation");
    }

    private String getUnit() {
        if (getArguments().getString("unit").equals("true")) {
            return "standard";
        } else {
            return "metric";
        }
    }

    private String getUnitMeasure() {
        if (getUnit().equals("standard")) {
            return "°F";
        } else if (getUnit().equals("metric")) {
            return "°C";
        }
        return null;
    }
}
