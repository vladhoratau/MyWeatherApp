package com.example.myweatherapp.repositories;

import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.myweatherapp.db.location.SavedLocation;
import com.example.myweatherapp.db.location.SavedLocationDao;
import com.example.myweatherapp.db.location.SavedLocationDatabase;

import java.util.List;

public class SavedLocationsRepository {

    private SavedLocationDao savedLocationDao;
    private LiveData<List<SavedLocation>> allSavedLocations;

    public SavedLocationsRepository() {
        SavedLocationDatabase savedLocationDatabase = SavedLocationDatabase.getInstance();
        savedLocationDao = savedLocationDatabase.savedLocationDao();
        allSavedLocations = savedLocationDao.getLocations();
    }

    public void insert(SavedLocation savedLocation) throws SQLiteConstraintException {
        new InsertSavedLocationAsyncTask(savedLocationDao).execute(savedLocation);
    }

    public void update(SavedLocation savedLocation) {
        new UpdateSavedLocationAsyncTask(savedLocationDao).execute(savedLocation);
    }

    public void delete(SavedLocation savedLocation) {
        new DeleteSavedLocationAsyncTask(savedLocationDao).execute(savedLocation);
    }

    public void deleteAllSavedLocations() {
        new DeleteAllSavedLocationAsyncTask(savedLocationDao).execute();
    }

    public LiveData<List<SavedLocation>> getAllSavedLocations() {
        return allSavedLocations;
    }

    private static class InsertSavedLocationAsyncTask extends AsyncTask<SavedLocation, Void, Void> {

        private SavedLocationDao savedLocationDao;

        private InsertSavedLocationAsyncTask(SavedLocationDao savedLocationDao) {
            this.savedLocationDao = savedLocationDao;
        }

        @Override
        protected Void doInBackground(SavedLocation... savedLocations) {
            try {
                savedLocationDao.insert(savedLocations[0]);
            } catch (SQLiteConstraintException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private static class UpdateSavedLocationAsyncTask extends AsyncTask<SavedLocation, Void, Void> {

        private SavedLocationDao savedLocationDao;

        private UpdateSavedLocationAsyncTask(SavedLocationDao savedLocationDao) {
            this.savedLocationDao = savedLocationDao;
        }

        @Override
        protected Void doInBackground(SavedLocation... savedLocations) {
            savedLocationDao.update(savedLocations[0]);
            return null;
        }
    }

    private static class DeleteSavedLocationAsyncTask extends AsyncTask<SavedLocation, Void, Void> {

        private SavedLocationDao savedLocationDao;

        private DeleteSavedLocationAsyncTask(SavedLocationDao savedLocationDao) {
            this.savedLocationDao = savedLocationDao;
        }

        @Override
        protected Void doInBackground(SavedLocation... savedLocations) {
            savedLocationDao.delete(savedLocations[0]);
            return null;
        }
    }

    private static class DeleteAllSavedLocationAsyncTask extends AsyncTask<Void, Void, Void> {

        private SavedLocationDao savedLocationDao;

        private DeleteAllSavedLocationAsyncTask(SavedLocationDao savedLocationDao) {
            this.savedLocationDao = savedLocationDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            savedLocationDao.deleteAllSavedLocations();
            return null;
        }
    }
}
