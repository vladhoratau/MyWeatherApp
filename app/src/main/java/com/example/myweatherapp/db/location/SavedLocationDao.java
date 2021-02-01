package com.example.myweatherapp.db.location;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SavedLocationDao {

    @Insert
    void insert(SavedLocation savedLocation);

    @Update
    void update(SavedLocation savedLocation);

    @Delete
    void delete(SavedLocation savedLocation);

    @Query("DELETE FROM locations_table")
    void deleteAllSavedLocations();

    @Query("SELECT * FROM locations_table ORDER BY cityName")
    LiveData<List<SavedLocation>> getLocations();
}
