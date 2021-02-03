package com.example.myweatherapp.views.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myweatherapp.R;
import com.example.myweatherapp.adapters.ViewPagerAdapter;
import com.example.myweatherapp.services.GpsTracker;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CurrentLocationFragment extends Fragment {

    private TodayWeatherDetailsFragment todayWeatherDetailsFragment;
    private TomorrowWeatherDetailsFragment tomorrowWeatherDetailsFragment;
    private FiveDaysWeatherDetailsFragment fiveDaysWeatherDetailsFragment;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager locationDetailsViewPager;
    private TabLayout locationDetailsTabLayout;
    private GpsTracker gpsTracker;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_current_location, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        locationDetailsTabLayout = view.findViewById(R.id.locationDetailsTabLayout);
        locationDetailsViewPager = view.findViewById(R.id.locationDetailsViewPager);

        Bundle bundle = new Bundle();
        bundle.putString("searchedLocation", getLocation());
        bundle.putString("unit", "metric");
        todayWeatherDetailsFragment = new TodayWeatherDetailsFragment();
        todayWeatherDetailsFragment.setArguments(bundle);
        tomorrowWeatherDetailsFragment = new TomorrowWeatherDetailsFragment();
        tomorrowWeatherDetailsFragment.setArguments(bundle);
        fiveDaysWeatherDetailsFragment = new FiveDaysWeatherDetailsFragment();
        fiveDaysWeatherDetailsFragment.setArguments(bundle);

        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), 0);
        viewPagerAdapter.addFragment(todayWeatherDetailsFragment, "Today");
        viewPagerAdapter.addFragment(tomorrowWeatherDetailsFragment, "Tomorrow");
        viewPagerAdapter.addFragment(fiveDaysWeatherDetailsFragment, "5 days");
        locationDetailsViewPager.setAdapter(viewPagerAdapter);
        locationDetailsTabLayout.setupWithViewPager(locationDetailsViewPager);
    }

    private String getLocation() {
        gpsTracker = new GpsTracker(getActivity());
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();

            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String address = addresses.get(0).getAddressLine(0);
            String cityNamePostalCode = address.split(",")[1];
            cityNamePostalCode = cityNamePostalCode.replaceAll("[0-9]", "");
            return cityNamePostalCode.trim();
        } else {
            gpsTracker.showSettingsAlert();
        }
        return "";
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        menu.clear();
    }
}
