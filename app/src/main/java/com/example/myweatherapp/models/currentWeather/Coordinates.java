package com.example.myweatherapp.models;

public class Coordinates {
    private Double lon;
    private Double lat;

    public Coordinates(Double lon, Double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public Double getLat() {
        return lat;
    }

}
