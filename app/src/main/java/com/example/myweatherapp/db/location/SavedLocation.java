package com.example.myweatherapp.db.location;

import android.widget.ImageView;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "locations_table", indices = {@Index(value = {"cityName"}, unique = true)})
public class SavedLocation {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String cityName;
    private String description;
    private Double temperature;
    private String unit;
    private String icon;

    public SavedLocation(String cityName, String description, Double temperature, String unit, String icon) {
        this.cityName = cityName;
        this.description = description;
        this.temperature = temperature;
        this.unit = unit;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public String getCityName() {
        return cityName;
    }

    public String getDescription() {
        return description;
    }

    public Double getTemperature() {
        return temperature;
    }

    public String getIcon() {
        return icon;
    }

    public String getUnit() {
        return unit;
    }

    public void setId(int id) {
        this.id = id;
    }
}
