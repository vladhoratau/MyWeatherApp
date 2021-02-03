package com.example.myweatherapp.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.example.myweatherapp.db.location.SavedLocation;
import com.example.myweatherapp.models.currentWeather.Coordinates;
import com.example.myweatherapp.models.currentWeather.CurrentWeatherResponse;
import com.example.myweatherapp.models.oneCallWeather.HourlyWeather.HourlyWeatherData;
import com.example.myweatherapp.utils.ApplicationClass;
import com.example.myweatherapp.utils.ToastMessage;
import com.example.myweatherapp.utils.UnitsUtil;
import com.example.myweatherapp.viewmodels.SavedLocationViewModel;
import com.example.myweatherapp.viewmodels.WeatherViewModel;

import com.google.android.material.textview.MaterialTextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class TodayWeatherDetailsFragment extends Fragment {

    private WeatherViewModel weatherViewModel;
    private SavedLocationViewModel savedLocationViewModel;
    private MaterialTextView cityName, temperature, feelsLike, description, imgUrl;
    private ImageView weatherIcon;
    private WeatherAdapter weatherAdapter;
    private RecyclerView todayHourlyWeather;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        imgUrl = view.findViewById(R.id.imageViewText);

        weatherAdapter = new WeatherAdapter();
        todayHourlyWeather.setLayoutManager(new LinearLayoutManager(getContext()));
        todayHourlyWeather.setAdapter(weatherAdapter);

        savedLocationViewModel = new ViewModelProvider(this).get(SavedLocationViewModel.class);
        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        weatherViewModel.init();

        weatherViewModel.getCurrentWeather(getSearchedLocation(), UnitsUtil.getUnit(getUnit()));
        weatherViewModel.getCurrentWeatherLiveData().observe(getViewLifecycleOwner(), new Observer<CurrentWeatherResponse>() {
            @Override
            public void onChanged(@Nullable CurrentWeatherResponse currentWeatherResponse) {
                if (ApplicationClass.getInstance().isNetworkConnected()) {
                    if (currentWeatherResponse != null) {
                        String unitMeasure = UnitsUtil.getUnitMeasure(UnitsUtil.getUnit(getUnit()));
                        cityName.setText(currentWeatherResponse.getCityName() + "," + " " + currentWeatherResponse.getCountry().getCountryName());
                        temperature.setText(currentWeatherResponse.getMainWeatherParams().getTemp().intValue() + unitMeasure);
                        description.setText(String.valueOf(currentWeatherResponse.getWeather().get(0).getDescription()));
                        feelsLike.setText("Feels like: " + (currentWeatherResponse.getMainWeatherParams().getFeelsLike().intValue()) + unitMeasure);

                        String weatherIconUrl = ApplicationClass.getInstance().getString(R.string.iconRoot)
                                + currentWeatherResponse.getWeather().get(0).getIcon() + ".png";
                        Picasso.with(getContext()).load(weatherIconUrl).into(weatherIcon);
                        imgUrl.setText(currentWeatherResponse.getWeather().get(0).getIcon());
                        getActivity().setTitle(currentWeatherResponse.getCityName()
                                + "," + " " + currentWeatherResponse.getCountry().getCountryName()
                        + ": " + currentWeatherResponse.getMainWeatherParams().getTemp().intValue() + unitMeasure
                        + ", " +  currentWeatherResponse.getWeather().get(0).getDescription());

                        if(getAddToFavStatus() != null && getAddToFavStatus().equals("true")) {
                            savedLocationViewModel.insert(new SavedLocation(
                                    currentWeatherResponse.getCityName(),
                                    currentWeatherResponse.getWeather().get(0).getDescription(),
                                    currentWeatherResponse.getMainWeatherParams().getTemp(),
                                    UnitsUtil.getUnit(getUnit()),
                                    currentWeatherResponse.getWeather().get(0).getIcon()));
                        }
                    } else {
                        ToastMessage.showMessage("Invalid location");
                    }
                } else {
                    ToastMessage.showMessage("No network connection");
                }
            }
        });

        weatherViewModel.getCoordinatesLiveData().observe(getViewLifecycleOwner(), new Observer<Coordinates>() {
            @Override
            public void onChanged(Coordinates coordinates) {
                weatherViewModel.getHourlyWeather(UnitsUtil.getUnit(getUnit()));
            }
        });

        weatherViewModel.getHourlyWeatherLiveData().observe(getViewLifecycleOwner(), new Observer<List<HourlyWeatherData>>() {
            @Override
            public void onChanged(List<HourlyWeatherData> hourlyWeatherDataList) {
                if (hourlyWeatherDataList != null) {
                    weatherAdapter.setResults(hourlyWeatherDataList, UnitsUtil.getUnitMeasure(UnitsUtil.getUnit(getUnit())));
                }
            }
        });

    }

    private String getUnit() {
        return getArguments().getString("unit");
    }

    private String getSearchedLocation() {
        return getArguments().getString("searchedLocation");
    }

    private String getAddToFavStatus() {
        return getArguments().getString("addToFavStatus");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addLocationToFav: {
                savedLocationViewModel.insert(new SavedLocation(
                        cityName.getText().toString(),
                        description.getText().toString(),
                        Double.valueOf(temperature.getText().toString().substring(0, temperature.getText().toString().length() - 2)),
                        UnitsUtil.getUnit(Objects.requireNonNull(getUnit())),
                        String.valueOf(imgUrl.getText())
                ));
                break;
            }
            case R.id.refresh: {
                weatherViewModel.getCurrentWeather(getSearchedLocation(), UnitsUtil.getUnit(getUnit()));
                ToastMessage.showMessage("New weather data updated!");
                break;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
