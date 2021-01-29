package com.example.myweatherapp.views.activities;

import com.example.myweatherapp.R;
import com.example.myweatherapp.adapters.ViewPagerAdapter;
import com.example.myweatherapp.views.fragments.CurrentLocationFragment;
import com.example.myweatherapp.views.fragments.LocationsFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {
    private ViewPager mainActivityViewPager;
    private TabLayout mainActivityTabLayout;
    private ViewPagerAdapter viewPagerAdapter;
    private FloatingActionButton searchFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mainActivityViewPager = findViewById(R.id.mainActivityViewPager);
        mainActivityTabLayout = findViewById(R.id.mainActivityTab);
        searchFAB = findViewById(R.id.searchFAB);
        CurrentLocationFragment currentLocationFragment = new CurrentLocationFragment();
        LocationsFragment locationsFragment = new LocationsFragment();

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(locationsFragment, "Locations");
        viewPagerAdapter.addFragment(currentLocationFragment, "Current Location");
        mainActivityViewPager.setAdapter(viewPagerAdapter);

        mainActivityTabLayout.setupWithViewPager(mainActivityViewPager);

        searchFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSearchActivity();
            }
        });
    }

    private void goToSearchActivity(){
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    }
}
