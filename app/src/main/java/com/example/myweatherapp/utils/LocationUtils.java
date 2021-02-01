package com.example.myweatherapp.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.example.myweatherapp.services.GpsTracker;
import com.example.myweatherapp.views.activities.SearchActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationUtils {

    public static String getLocation(Context context) {
        GpsTracker gpsTracker = new GpsTracker(context);
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();

            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
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
}
