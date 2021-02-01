package com.example.myweatherapp.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myweatherapp.R;
import com.example.myweatherapp.adapters.SavedLocationAdapter;
import com.example.myweatherapp.db.location.SavedLocation;
import com.example.myweatherapp.utils.ToastMessage;
import com.example.myweatherapp.viewmodels.SavedLocationViewModel;

import java.util.List;

public class LocationsFragment extends Fragment {
    private SavedLocationViewModel savedLocationViewModel;
    private RecyclerView savedLocationsRecycleView;
    private SavedLocationAdapter savedLocationAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_locations, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        savedLocationsRecycleView = view.findViewById(R.id.savedLocationsRecycleView);
        savedLocationsRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        savedLocationAdapter = new SavedLocationAdapter();
        savedLocationsRecycleView.setAdapter(savedLocationAdapter);

        savedLocationViewModel = new ViewModelProvider(this).get(SavedLocationViewModel.class);
//        SavedLocation savedLocation = new SavedLocation("Oradea","frumos afara", 30.0, "13n");
//        savedLocationViewModel.insert(savedLocation);
        savedLocationViewModel.getAllSavedLocations().observe(getViewLifecycleOwner(), new Observer<List<SavedLocation>>() {
            @Override
            public void onChanged(List<SavedLocation> savedLocations) {
                savedLocationAdapter.setSavedLoactions(savedLocations);
            }
        });
    }

}
