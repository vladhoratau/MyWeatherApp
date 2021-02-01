package com.example.myweatherapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweatherapp.R;
import com.example.myweatherapp.models.OneCallWeather.HourlyWeather.HourlyWeatherData;
import com.example.myweatherapp.utils.ApplicationClass;
import com.example.myweatherapp.utils.DateUtil;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherResultHolder> {
    private List<HourlyWeatherData> hourlyWeatherDataList = new ArrayList<>();
    private String unitMeasure;

    @NonNull
    @Override
    public WeatherResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_item, parent, false);

        return new WeatherResultHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherResultHolder holder, int position) {
        HourlyWeatherData hourlyWeatherData = hourlyWeatherDataList.get(position);

        holder.temperatureTextView.setText(hourlyWeatherData.getTemp().intValue() + unitMeasure);
        holder.descriptionTextView.setText(String.valueOf(hourlyWeatherData.getWeatherDataInfoList().get(0).getDescription()));
        String weatherIconUrl = ApplicationClass.getInstance().getString(R.string.iconRoot)
                + hourlyWeatherData.getWeatherDataInfoList().get(0).getIcon() + ".png";
        Picasso.with(ApplicationClass.getInstance().getApplicationContext()).load(weatherIconUrl).into(holder.weatherIconImageView);
        holder.hourTextView.setText(getFormattedHour(DateUtil.getHourFromUnix(hourlyWeatherData.getDt())));
    }


    @Override
    public int getItemCount() {
        return hourlyWeatherDataList.size();
    }

    public void setResults(List<HourlyWeatherData> hourlyWeatherDataList, String unitMeasure) {
        this.hourlyWeatherDataList = hourlyWeatherDataList;
        this.unitMeasure = unitMeasure;
        notifyDataSetChanged();
    }

    private String getFormattedHour(Integer hour) {
        if (hour < 10) {
            return "0" + hour + ":00";
        } else {
            return hour + ":00";
        }
    }

    class WeatherResultHolder extends RecyclerView.ViewHolder {
        private MaterialTextView temperatureTextView;
        private MaterialTextView hourTextView;
        private MaterialTextView descriptionTextView;
        private ImageView weatherIconImageView;

        public WeatherResultHolder(@NonNull View itemView) {
            super(itemView);

            temperatureTextView = itemView.findViewById(R.id.temperatureItem);
            hourTextView = itemView.findViewById(R.id.timeItem);
            descriptionTextView = itemView.findViewById(R.id.descriptionItem);
            weatherIconImageView = itemView.findViewById(R.id.weatherIconItem);
        }
    }
}
