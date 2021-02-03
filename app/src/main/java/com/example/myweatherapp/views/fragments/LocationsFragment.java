package com.example.myweatherapp.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweatherapp.R;
import com.example.myweatherapp.adapters.SavedLocationAdapter;
import com.example.myweatherapp.db.location.SavedLocation;
import com.example.myweatherapp.utils.ToastMessage;
import com.example.myweatherapp.viewmodels.SavedLocationViewModel;
import com.example.myweatherapp.viewmodels.WeatherViewModel;
import com.example.myweatherapp.views.activities.LocationDetailsActivity;

import java.util.List;

public class LocationsFragment extends Fragment {

    private SavedLocationViewModel savedLocationViewModel;
    private RecyclerView savedLocationsRecycleView;
    private SavedLocationAdapter savedLocationAdapter;
    private WeatherViewModel weatherViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

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

        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        weatherViewModel.init();
        savedLocationViewModel = new ViewModelProvider(this).get(SavedLocationViewModel.class);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                savedLocationViewModel.delete(savedLocationAdapter.getSavedLocationAt(viewHolder.getAdapterPosition()));
                ToastMessage.showMessage("Saved location deleted");
            }
        }).attachToRecyclerView(savedLocationsRecycleView);

        savedLocationAdapter.setOnItemClickListener(new SavedLocationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SavedLocation savedLocation) {
                final String searchLocationText = String.valueOf(savedLocation.getCityName());
                final Intent intent = new Intent(getContext(), LocationDetailsActivity.class);
                intent.putExtra("unit", savedLocation.getUnit());
                Log.e("unit", savedLocation.getUnit());
                intent.putExtra("searchedLocation", searchLocationText);
                startActivity(intent);
            }
        });

        savedLocationViewModel.getAllSavedLocations().observe(getViewLifecycleOwner(), new Observer<List<SavedLocation>>() {
            @Override
            public void onChanged(List<SavedLocation> savedLocations) {
                savedLocationAdapter.setSavedLocations(savedLocations);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteAllSavedLocations: {
                savedLocationViewModel.deleteAllSavedLocations();
                ToastMessage.showMessage("All saved locations were deleted.");
                break;
            }
            case R.id.search: {
                SearchView searchView = (SearchView) item.getActionView();
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        savedLocationAdapter.getFilter().filter(newText);
                        return false;
                    }
                });
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
