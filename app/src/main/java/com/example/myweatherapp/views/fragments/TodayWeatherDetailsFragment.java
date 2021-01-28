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

import com.example.myweatherapp.R;
import com.example.myweatherapp.models.currentWeather.CurrentWeatherResponse;
import com.example.myweatherapp.viewmodels.GetCurrentWeatherViewModel;
import com.google.android.material.textview.MaterialTextView;

public class TodayWeatherDetailsFragment extends Fragment {
    private GetCurrentWeatherViewModel getCurrentWeatherViewModel;
    private MaterialTextView cityName, temperature, feelsLike, description;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today_weather_details, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cityName = view.findViewById(R.id.cityName);
        temperature = view.findViewById(R.id.temperature);
        feelsLike = view.findViewById(R.id.feelsLike);
        description = view.findViewById(R.id.description);

        String searchedLocation = getArguments().getString("searchedLocation");
        String unit = getArguments().getString("unit");

        getCurrentWeatherViewModel = new ViewModelProvider(this).get(GetCurrentWeatherViewModel.class);
        getCurrentWeatherViewModel.init();

        getCurrentWeatherViewModel.getCurrentWeather(searchedLocation, getUnit());
        getCurrentWeatherViewModel.getCurrentWeatherLiveData().observe(getViewLifecycleOwner(), new Observer<CurrentWeatherResponse>() {
            @Override
            public void onChanged(CurrentWeatherResponse currentWeatherResponse) {
                if (currentWeatherResponse != null) {
                    cityName.setText(currentWeatherResponse.getCityName());
                    temperature.setText(String.valueOf(currentWeatherResponse.getMainWeatherParams().getTemp()));
                    description.setText(String.valueOf(currentWeatherResponse.getWeather().get(0).getDescription()));
                    feelsLike.setText(String.valueOf(currentWeatherResponse.getMainWeatherParams().getFeelsLike()));
                }
            }
        });
    }

    private String getUnit() {
        if (getArguments().getString("unit").equals("true")) {
            return "standard";
        } else {
            return "metric";
        }
    }
}
