package com.example.myweatherapp.views.activities;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.myweatherapp.R;
import com.example.myweatherapp.adapters.ViewPagerAdapter;
import com.example.myweatherapp.views.fragments.FiveDaysWeatherDetailsFragment;
import com.example.myweatherapp.views.fragments.TodayWeatherDetailsFragment;
import com.example.myweatherapp.views.fragments.TomorrowWeatherDetailsFragment;
import com.google.android.material.tabs.TabLayout;

public class LocationDetailsActivity extends AppCompatActivity {
    private TodayWeatherDetailsFragment todayWeatherDetailsFragment;
    private TomorrowWeatherDetailsFragment tommorowWeatherDetailsFragment;
    private FiveDaysWeatherDetailsFragment fiveDaysWeatherDetailsFragment;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager locationDetailsViewPager;
    private TabLayout locationDetailsTabLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);

        locationDetailsTabLayout = findViewById(R.id.locationDetailsTabLayout);
        locationDetailsViewPager = findViewById(R.id.locationDetailsViewPager);

        Intent passedWeatherData = getIntent();
        String searchedLocation = passedWeatherData.getStringExtra("searchedLocation");
        String unit = passedWeatherData.getStringExtra("unit");

        Bundle bundle = new Bundle();
        bundle.putString("searchedLocation", searchedLocation);
        bundle.putString("unit", unit);
        todayWeatherDetailsFragment = new TodayWeatherDetailsFragment();
        todayWeatherDetailsFragment.setArguments(bundle);
        tommorowWeatherDetailsFragment = new TomorrowWeatherDetailsFragment();
        tommorowWeatherDetailsFragment.setArguments(bundle);
        fiveDaysWeatherDetailsFragment = new FiveDaysWeatherDetailsFragment();
        fiveDaysWeatherDetailsFragment.setArguments(bundle);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(todayWeatherDetailsFragment, "Today");
        viewPagerAdapter.addFragment(tommorowWeatherDetailsFragment, "Tomorrow");
        viewPagerAdapter.addFragment(fiveDaysWeatherDetailsFragment, "5 days");
        locationDetailsViewPager.setAdapter(viewPagerAdapter);
        locationDetailsTabLayout.setupWithViewPager(locationDetailsViewPager);


    }

}
