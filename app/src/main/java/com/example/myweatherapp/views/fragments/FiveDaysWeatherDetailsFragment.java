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
import com.example.myweatherapp.adapters.DailyWeatherAdapter;
import com.example.myweatherapp.adapters.HistoricalWeatherAdapter;
import com.example.myweatherapp.adapters.WeatherAdapter;
import com.example.myweatherapp.models.OneCallWeather.DailyWeather.DailyWeatherData;
import com.example.myweatherapp.models.OneCallWeather.HourlyWeather.HourlyWeatherData;
import com.example.myweatherapp.models.currentWeather.Coordinates;
import com.example.myweatherapp.viewmodels.WeatherViewModel;
import com.google.android.material.textview.MaterialTextView;

import org.apache.commons.lang3.text.WordUtils;

import java.util.List;

public class FiveDaysWeatherDetailsFragment extends Fragment {
    private WeatherViewModel weatherViewModel;
    private DailyWeatherAdapter dailyWeatherAdapter;
    private HistoricalWeatherAdapter historicalWeatherDataAdapter;
    private RecyclerView fiveDaysDailyWeather;
    //private RecyclerView historicalWeather;
    private MaterialTextView cityName;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fivedays_weather_details, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fiveDaysDailyWeather = view.findViewById(R.id.fiveDaysDailyyWeather);
        //historicalWeather = view.findViewById(R.id.historicalWeather);
        cityName = view.findViewById(R.id.fiveDaysCityName);

        cityName.setText(WordUtils.capitalize(getSearchedLocation(),',', '-'));

        dailyWeatherAdapter = new DailyWeatherAdapter();
        fiveDaysDailyWeather.setLayoutManager(new LinearLayoutManager(getContext()));


        historicalWeatherDataAdapter = new HistoricalWeatherAdapter();
        fiveDaysDailyWeather.setAdapter(dailyWeatherAdapter);
        //fiveDaysDailyWeather.setAdapter(historicalWeatherDataAdapter);
//        historicalWeather.setLayoutManager(new LinearLayoutManager(getContext()));
//        historicalWeather.setAdapter(historicalWeatherDataAdapter);

        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        weatherViewModel.init();

        weatherViewModel.getCurrentWeather(getSearchedLocation(),getUnit());

        weatherViewModel.getCoordinatesLiveData().observe(getViewLifecycleOwner(), new Observer<Coordinates>() {
            @Override
            public void onChanged(Coordinates coordinates) {
                weatherViewModel.getDailyWeather(getUnit());
                weatherViewModel.getHistoricalWeather(getUnit());
            }
        });

        weatherViewModel.getDailyWeatherDataLiveData().observe(getViewLifecycleOwner(), new Observer<List<DailyWeatherData>>() {
            @Override
            public void onChanged(List<DailyWeatherData> dailyWeatherData) {
                if(dailyWeatherData != null) {
                    dailyWeatherAdapter.setResults(dailyWeatherData, getUnitMeasure());
                }
            }
        });

//        weatherViewModel.getHistoricalWeatherDataLiveData().observe(getViewLifecycleOwner(), new Observer<List<HourlyWeatherData>>() {
//            @Override
//            public void onChanged(List<HourlyWeatherData> historicalWeatherData) {
//                if(historicalWeatherData != null) {
//                    historicalWeatherDataAdapter.setResults(historicalWeatherData, getUnitMeasure());
//                }
//            }
//        });

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

