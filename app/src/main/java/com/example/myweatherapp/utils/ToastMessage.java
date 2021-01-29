package com.example.myweatherapp.utils;

import android.widget.Toast;

public class ToastMessage {

    public static void showMessage(String message) {
        Toast.makeText(ApplicationClass.getInstance(), message, Toast.LENGTH_SHORT).show();
    }
}
