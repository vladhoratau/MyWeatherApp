package com.example.myweatherapp.views.activities;

import android.content.Intent;
import android.os.Bundle;


import com.example.myweatherapp.R;
import com.example.myweatherapp.views.fragments.TodayWeatherDetailsFragment;

public class LocationDetailsActivity extends BaseActivity {
    private TodayWeatherDetailsFragment todayWeatherDetailsFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);

        Intent passedWeatherData = getIntent();
        String searchedLocation = passedWeatherData.getStringExtra("searchedLocation");
        String unit = passedWeatherData.getStringExtra("unit");

        Bundle bundle = new Bundle();
        bundle.putString("searchedLocation", searchedLocation);
        bundle.putString("unit", unit);
        todayWeatherDetailsFragment = new TodayWeatherDetailsFragment();
        todayWeatherDetailsFragment.setArguments(bundle);
        addFragment(R.id.todayWeather, todayWeatherDetailsFragment, "todayWeatherFragment");

    }
}
