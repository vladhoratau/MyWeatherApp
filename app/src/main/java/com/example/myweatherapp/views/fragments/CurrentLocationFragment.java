package com.example.myweatherapp.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myweatherapp.R;
import com.example.myweatherapp.adapters.ViewPagerAdapter;
import com.example.myweatherapp.utils.LocationUtils;
import com.google.android.material.tabs.TabLayout;

public class CurrentLocationFragment extends Fragment {
    private TodayWeatherDetailsFragment todayWeatherDetailsFragment;
    private TomorrowWeatherDetailsFragment tommorowWeatherDetailsFragment;
    private FiveDaysWeatherDetailsFragment fiveDaysWeatherDetailsFragment;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager locationDetailsViewPager;
    private TabLayout locationDetailsTabLayout;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_current_location, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        locationDetailsTabLayout = view.findViewById(R.id.locationDetailsTabLayout);
        locationDetailsViewPager = view.findViewById(R.id.locationDetailsViewPager);

        Bundle bundle = new Bundle();
        bundle.putString("searchedLocation", LocationUtils.getLocation());
        bundle.putString("unit", "metric");
        todayWeatherDetailsFragment = new TodayWeatherDetailsFragment();
        todayWeatherDetailsFragment.setArguments(bundle);
        tommorowWeatherDetailsFragment = new TomorrowWeatherDetailsFragment();
        tommorowWeatherDetailsFragment.setArguments(bundle);
        fiveDaysWeatherDetailsFragment = new FiveDaysWeatherDetailsFragment();
        fiveDaysWeatherDetailsFragment.setArguments(bundle);

        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), 0);
        viewPagerAdapter.addFragment(todayWeatherDetailsFragment, "Today");
        viewPagerAdapter.addFragment(tommorowWeatherDetailsFragment, "Tomorrow");
        viewPagerAdapter.addFragment(fiveDaysWeatherDetailsFragment, "5 days");
        locationDetailsViewPager.setAdapter(viewPagerAdapter);
        locationDetailsTabLayout.setupWithViewPager(locationDetailsViewPager);
    }


}
