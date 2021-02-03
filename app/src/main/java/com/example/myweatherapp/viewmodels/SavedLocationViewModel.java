package com.example.myweatherapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myweatherapp.db.location.SavedLocation;
import com.example.myweatherapp.repositories.SavedLocationsRepository;

import java.util.List;

public class SavedLocationViewModel extends ViewModel {

    private SavedLocationsRepository savedLocationsRepository;
    private LiveData<List<SavedLocation>> allSavedLocations;

    public SavedLocationViewModel() {
        savedLocationsRepository = new SavedLocationsRepository();
        allSavedLocations = savedLocationsRepository.getAllSavedLocations();
    }

    public void insert(SavedLocation savedLocation) {
        savedLocationsRepository.insert(savedLocation);
    }

    public void update(SavedLocation savedLocation) {
        savedLocationsRepository.update(savedLocation);
    }

    public void delete(SavedLocation savedLocation) {
        savedLocationsRepository.delete(savedLocation);
    }

    public void deleteAllSavedLocations() {
        savedLocationsRepository.deleteAllSavedLocations();
    }

    public LiveData<List<SavedLocation>> getAllSavedLocations() {
        return allSavedLocations;
    }
}
