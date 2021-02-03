package com.example.myweatherapp.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.example.myweatherapp.models.oneCallWeather.DailyWeather.DailyWeatherData;
import com.example.myweatherapp.models.currentWeather.Coordinates;
import com.example.myweatherapp.utils.ToastMessage;
import com.example.myweatherapp.utils.UnitsUtil;
import com.example.myweatherapp.viewmodels.WeatherViewModel;

import com.google.android.material.textview.MaterialTextView;

import org.apache.commons.lang3.text.WordUtils;

import java.util.List;

public class FiveDaysWeatherDetailsFragment extends Fragment {

    private WeatherViewModel weatherViewModel;
    private DailyWeatherAdapter dailyWeatherAdapter;
    private RecyclerView fiveDaysDailyWeather;
    private MaterialTextView cityName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fivedays_weather_details, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fiveDaysDailyWeather = view.findViewById(R.id.fiveDaysDailyWeather);
        cityName = view.findViewById(R.id.fiveDaysCityName);

        cityName.setText(WordUtils.capitalize(getSearchedLocation(),',', '-'));

        dailyWeatherAdapter = new DailyWeatherAdapter();
        fiveDaysDailyWeather.setLayoutManager(new LinearLayoutManager(getContext()));
        fiveDaysDailyWeather.setAdapter(dailyWeatherAdapter);

        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        weatherViewModel.init();

        weatherViewModel.getCurrentWeather(getSearchedLocation(), UnitsUtil.getUnit(getUnit()));

        weatherViewModel.getCoordinatesLiveData().observe(getViewLifecycleOwner(), new Observer<Coordinates>() {
            @Override
            public void onChanged(Coordinates coordinates) {
                weatherViewModel.getDailyWeather(UnitsUtil.getUnit(getUnit()));
                weatherViewModel.getHistoricalWeather(UnitsUtil.getUnit(getUnit()));
            }
        });

        weatherViewModel.getDailyWeatherDataLiveData().observe(getViewLifecycleOwner(), new Observer<List<DailyWeatherData>>() {
            @Override
            public void onChanged(List<DailyWeatherData> dailyWeatherData) {
                if(dailyWeatherData != null) {
                    dailyWeatherAdapter.setResults(dailyWeatherData, UnitsUtil.getUnitMeasure(UnitsUtil.getUnit(getUnit())));
                }
            }
        });
    }

    private String getSearchedLocation() {
        return getArguments().getString("searchedLocation");
    }

    private String getUnit() {
        return getArguments().getString("unit");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh: {
                weatherViewModel.getCurrentWeather(getSearchedLocation(), UnitsUtil.getUnit(getUnit()));
                ToastMessage.showMessage("New weather data updated!");
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        menu.clear();
    }
}

