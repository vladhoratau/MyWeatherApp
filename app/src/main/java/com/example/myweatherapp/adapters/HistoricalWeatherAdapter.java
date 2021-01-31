package com.example.myweatherapp.adapters;

import android.util.Log;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HistoricalWeatherAdapter extends RecyclerView.Adapter<HistoricalWeatherAdapter.HistoricalWeatherResultHolder> {
    private List<HourlyWeatherData> historicalWeatherDataList = new ArrayList<>();
    private String unitMeasure;

    @NonNull
    @Override
    public HistoricalWeatherResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.five_days_weather_item, parent, false);

        return new HistoricalWeatherResultHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull HistoricalWeatherResultHolder holder, int position) {
        HourlyWeatherData hourlyWeatherData = historicalWeatherDataList.get(position);

        holder.dayTemperatureTextView.setText(hourlyWeatherData.getTemp().intValue() + unitMeasure);
        holder.nightTemperatureTextView.setText(hourlyWeatherData.getTemp().intValue() - 2 + unitMeasure);
        holder.descriptionTextView.setText(String.valueOf(hourlyWeatherData.getHourlyWeatherDataInfoList().get(0).getDescription()));
        String weatherIconUrl = ApplicationClass.getInstance().getString(R.string.iconRoot)
                + hourlyWeatherData.getHourlyWeatherDataInfoList().get(0).getIcon() + ".png";
        Picasso.with(ApplicationClass.getInstance().getApplicationContext()).load(weatherIconUrl).into(holder.weatherIconImageView);
        holder.dayTextView.setText(DateUtil.getDayFromUnix(hourlyWeatherData.getDt()));
    }


    @Override
    public int getItemCount() {
        return historicalWeatherDataList.size();
    }

    public void setResults(List<HourlyWeatherData> hourlyWeatherDataList, String unitMeasure) {
        this.historicalWeatherDataList = hourlyWeatherDataList;
        hourlyWeatherDataList.sort(Comparator.comparingLong(HourlyWeatherData::getDt));
        this.unitMeasure = unitMeasure;
        notifyDataSetChanged();
    }


    class HistoricalWeatherResultHolder extends RecyclerView.ViewHolder {
        private TextView dayTemperatureTextView;
        private TextView nightTemperatureTextView;
        private TextView dayTextView;
        private TextView descriptionTextView;
        private ImageView weatherIconImageView;

        public HistoricalWeatherResultHolder(@NonNull View itemView) {
            super(itemView);

            dayTemperatureTextView = itemView.findViewById(R.id.dayTemperatureItem);
            nightTemperatureTextView = itemView.findViewById(R.id.nightTemperatureItem);
            dayTextView = itemView.findViewById(R.id.dayItem);
            descriptionTextView = itemView.findViewById(R.id.descriptionItem);
            weatherIconImageView = itemView.findViewById(R.id.weatherIconItem);
        }
    }
}

