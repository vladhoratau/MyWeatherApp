package com.example.myweatherapp.utils;

import android.app.Application;

public class ApplicationClass extends Application {

    private static ApplicationClass applicationClass;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationClass = this;
    }

    public static ApplicationClass getInstance() {
        return applicationClass;
    }
}
