package com.example.myweatherapp.views.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.myweatherapp.R;
import com.example.myweatherapp.services.GpsTracker;
import com.example.myweatherapp.utils.ApplicationClass;
import com.example.myweatherapp.viewmodels.WeatherViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import static com.example.myweatherapp.utils.LocationUtils.getLocation;

public class SearchActivity extends AppCompatActivity {
    private TextInputEditText searchLocation;
    private TextInputLayout searchLocationLayout;
    private SwitchMaterial setUnit, addToFavourite;
    private MaterialButton viewWeather;
    private WeatherViewModel weatherViewModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        searchLocation = findViewById(R.id.searchLocation);
        searchLocationLayout = findViewById(R.id.searchLocationLayout);
        setUnit = findViewById(R.id.setUnit);
        addToFavourite = findViewById(R.id.addToFavourite);
        viewWeather = findViewById(R.id.viewWeather);

        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        weatherViewModel.init();

        searchLocationLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (ActivityCompat.checkSelfPermission(ApplicationClass.getInstance(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(ApplicationClass.getInstance(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SearchActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                searchLocation.setText(getLocation());
            }
        });

        viewWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String searchLocationText = String.valueOf(searchLocation.getText());
                final Intent intent = new Intent(SearchActivity.this, LocationDetailsActivity.class);
                intent.putExtra("unit", String.valueOf(setUnit.isChecked()));
                intent.putExtra("searchedLocation", searchLocationText);
                startActivity(intent);
            }
        });
    }

}
