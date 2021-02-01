package com.example.myweatherapp.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
import com.example.myweatherapp.models.currentWeather.CurrentWeatherResponse;
import com.example.myweatherapp.models.OneCallWeather.HourlyWeather.HourlyWeatherData;
import com.example.myweatherapp.utils.ApplicationClass;
import com.example.myweatherapp.utils.ToastMessage;
import com.example.myweatherapp.viewmodels.WeatherViewModel;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TodayWeatherDetailsFragment extends Fragment {
    private WeatherViewModel weatherViewModel;
    private MaterialTextView cityName, temperature, feelsLike, description;
    private ImageView weatherIcon;
    private WeatherAdapter weatherAdapter;
    private RecyclerView todayHourlyWeather;

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
        weatherIcon = view.findViewById(R.id.weatherIcon);
        todayHourlyWeather = view.findViewById(R.id.todayHourlyWeather);

        weatherAdapter = new WeatherAdapter();
        todayHourlyWeather.setLayoutManager(new LinearLayoutManager(getContext()));
        todayHourlyWeather.setAdapter(weatherAdapter);

        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        weatherViewModel.init();

        weatherViewModel.getCurrentWeather(getSearchedLocation(), getUnit());
        weatherViewModel.getCurrentWeatherLiveData().observe(getViewLifecycleOwner(), new Observer<CurrentWeatherResponse>() {
            @Override
            public void onChanged(@Nullable CurrentWeatherResponse currentWeatherResponse) {
                if (ApplicationClass.getInstance().isNetworkConnected()) {
                    if (currentWeatherResponse != null) {
                        cityName.setText(currentWeatherResponse.getCityName() + "," + " " + currentWeatherResponse.getCountry().getCountryName());
                        temperature.setText(currentWeatherResponse.getMainWeatherParams().getIntTemp() + getUnitMeasure());
                        description.setText(String.valueOf(currentWeatherResponse.getWeather().get(0).getDescription()));
                        feelsLike.setText("Feels like: " + (currentWeatherResponse.getMainWeatherParams().getIntFeelsLike()) + getUnitMeasure());

                        String weatherIconUrl = ApplicationClass.getInstance().getString(R.string.iconRoot)
                                + currentWeatherResponse.getWeather().get(0).getIcon() + ".png";
                        Picasso.with(getContext()).load(weatherIconUrl).into(weatherIcon);
                        getActivity().setTitle(currentWeatherResponse.getCityName()
                                + "," + " " + currentWeatherResponse.getCountry().getCountryName()
                        + ": " + currentWeatherResponse.getMainWeatherParams().getIntTemp() + getUnitMeasure()
                        + ", " +  currentWeatherResponse.getWeather().get(0).getDescription());

                    } else {
                        ToastMessage.showMessage("Invalid location");
//                        final Intent intent = new Intent(getActivity(), SearchActivity.class);
//                        startActivity(intent);
                    }
                } else {
                    ToastMessage.showMessage("No network connection");
//                    final Intent intent = new Intent(getActivity(), SearchActivity.class);
//                    startActivity(intent);
                }
            }
        });

        weatherViewModel.getCoordinatesLiveData().observe(getViewLifecycleOwner(), new Observer<Coordinates>() {
            @Override
            public void onChanged(Coordinates coordinates) {
                weatherViewModel.getHourlyWeather(getUnit());
            }
        });

        weatherViewModel.getHourlyWeatherLiveData().observe(getViewLifecycleOwner(), new Observer<List<HourlyWeatherData>>() {
            @Override
            public void onChanged(List<HourlyWeatherData> hourlyWeatherDataList) {
                if (hourlyWeatherDataList != null) {
                    weatherAdapter.setResults(hourlyWeatherDataList, getUnitMeasure());
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
