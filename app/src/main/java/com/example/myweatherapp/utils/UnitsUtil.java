package com.example.myweatherapp.utils;

public class UnitsUtil {

    public static String getUnitMeasure(String unit) {
        if (unit.equals("standard")) {
            return "°F";
        } else if (unit.equals("metric")) {
            return "°C";
        }
        return null;
    }

    public static String getUnit(String unit) {
        if (unit.equals("true")) {
            return "standard";
        } else {
            return "metric";
        }
    }
}
