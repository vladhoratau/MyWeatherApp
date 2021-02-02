package com.example.myweatherapp.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GpsStatusViewModel extends ViewModel {
    private MutableLiveData<Boolean> gpsStatusMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Boolean> getGpsStatusMutableLiveData() {
        return gpsStatusMutableLiveData;
    }

    public void setGpsStatus(Boolean status) {
        gpsStatusMutableLiveData.postValue(status);
    }
}
