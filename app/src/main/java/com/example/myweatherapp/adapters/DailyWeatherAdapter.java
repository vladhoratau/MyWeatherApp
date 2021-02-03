package com.example.myweatherapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweatherapp.R;
import com.example.myweatherapp.models.oneCallWeather.DailyWeather.DailyWeatherData;
import com.example.myweatherapp.utils.ApplicationClass;
import com.example.myweatherapp.utils.DateUtil;

import com.google.android.material.textview.MaterialTextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DailyWeatherAdapter extends RecyclerView.Adapter<DailyWeatherAdapter.WeatherResultHolder> {

    private List<DailyWeatherData> dailyWeatherDataList = new ArrayList<>();
    private String unitMeasure;

    @NonNull
    @Override
    public WeatherResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.five_days_weather_item, parent, false);

        return new WeatherResultHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherResultHolder holder, int position) {
        DailyWeatherData dailyWeatherData = dailyWeatherDataList.get(position);

        holder.dayTemperatureTextView.setText((dailyWeatherData.getTemp().getDay()).intValue() + unitMeasure);
        holder.nightTemperatureTextView.setText((dailyWeatherData.getTemp().getNight()).intValue() + unitMeasure);
        holder.descriptionTextView.setText(String.valueOf(dailyWeatherData.getDailyWeatherDataInfoList().get(0).getDescription()));
        String weatherIconUrl = ApplicationClass.getInstance().getString(R.string.iconRoot)
                + dailyWeatherData.getDailyWeatherDataInfoList().get(0).getIcon() + ".png";
        Picasso.with(ApplicationClass.getInstance().getApplicationContext()).load(weatherIconUrl).into(holder.weatherIconImageView);
        holder.dayTextView.setText((DateUtil.getDayFromUnix(dailyWeatherData.getDt())));
    }

    @Override
    public int getItemCount() {
        return dailyWeatherDataList.size();
    }

    public void setResults(List<DailyWeatherData> dailyWeatherDataList, String unitMeasure) {
        this.dailyWeatherDataList = dailyWeatherDataList;
        dailyWeatherDataList.sort(Comparator.comparing(DailyWeatherData::getDt));
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
        private MaterialTextView dayTemperatureTextView;
        private MaterialTextView nightTemperatureTextView;
        private MaterialTextView dayTextView;
        private MaterialTextView descriptionTextView;
        private ImageView weatherIconImageView;

        public WeatherResultHolder(@NonNull View itemView) {
            super(itemView);

            dayTemperatureTextView = itemView.findViewById(R.id.dayTemperatureItem);
            nightTemperatureTextView = itemView.findViewById(R.id.nightTemperatureItem);
            dayTextView = itemView.findViewById(R.id.dayItem);
            descriptionTextView = itemView.findViewById(R.id.descriptionItem);
            weatherIconImageView = itemView.findViewById(R.id.weatherIconItem);
        }
    }
}