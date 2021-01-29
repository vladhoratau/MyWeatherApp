package com.example.myweatherapp.utils;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

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

    public boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
