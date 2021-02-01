package com.example.myweatherapp.db.location;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myweatherapp.utils.ApplicationClass;

@Database(entities = {SavedLocation.class}, version = 1)
public abstract class  SavedLocationDatabase extends RoomDatabase {
    private static SavedLocationDatabase instance;
    public abstract SavedLocationDao savedLocationDao();

    public static synchronized SavedLocationDatabase getInstance() {
        if(instance == null) {
            instance = Room.databaseBuilder(ApplicationClass.getInstance().getBaseContext(), SavedLocationDatabase.class, "locations_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
