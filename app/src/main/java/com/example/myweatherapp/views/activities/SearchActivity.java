package com.example.myweatherapp.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myweatherapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

public class SearchActivity extends BaseActivity {
    private TextInputEditText searchLocation;
    private SwitchMaterial setUnit, addToFavourite;
    private MaterialButton viewWeather;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);

        searchLocation = findViewById(R.id.searchLocation);
        setUnit = findViewById(R.id.setUnit);
        addToFavourite = findViewById(R.id.addToFavourite);
        viewWeather = findViewById(R.id.viewWeather);

        viewWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String searchLocationString = searchLocation.getText().toString();
                final Intent intent = new Intent(SearchActivity.this, LocationDetailsActivity.class);
                intent.putExtra("unit", String.valueOf(setUnit.isChecked()));
                intent.putExtra("searchedLocation", searchLocationString);
                startActivity(intent);
            }
        });
    }
}
