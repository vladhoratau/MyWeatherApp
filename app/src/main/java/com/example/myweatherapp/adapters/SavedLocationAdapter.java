package com.example.myweatherapp.adapters;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweatherapp.R;
import com.example.myweatherapp.db.location.SavedLocation;
import com.example.myweatherapp.utils.ApplicationClass;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SavedLocationAdapter extends RecyclerView.Adapter<SavedLocationAdapter.SavedLocationHolder> {
    private List<SavedLocation> savedLoactions = new ArrayList<>();

    @NonNull
    @Override
    public SavedLocationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.savedlocations_item, parent, false);
        return new SavedLocationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedLocationHolder holder, int position) {
        holder.savedLocationTemperatureAndDescription.setText(savedLoactions.get(position).getTemperature().intValue()
                + " " + savedLoactions.get(position).getUnit() + ", "
                + savedLoactions.get(position).getDescription());
        holder.savedLocationCityName.setText(savedLoactions.get(position).getCityName());
        String weatherIconUrl = ApplicationClass.getInstance().getString(R.string.iconRoot)
                + savedLoactions.get(position).getIcon() + ".png";
        Picasso.with(ApplicationClass.getInstance().getApplicationContext()).load(weatherIconUrl).into(holder.savedLocationIcon);
    }

    public void setSavedLoactions(List<SavedLocation> savedLoactions) {
        this.savedLoactions = savedLoactions;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return savedLoactions.size();
    }

    class SavedLocationHolder extends RecyclerView.ViewHolder {
        private MaterialTextView savedLocationTemperatureAndDescription;
        private MaterialTextView savedLocationCityName;
        private ImageView savedLocationIcon;

        public SavedLocationHolder(@NonNull View itemView) {
            super(itemView);

            savedLocationTemperatureAndDescription = itemView.findViewById(R.id.savedLocationTemperatureAndDescriptionItem);
            savedLocationCityName = itemView.findViewById(R.id.savedLocationCityNameItem);
            savedLocationIcon = itemView.findViewById(R.id.savedLocationIconItem);
        }
    }
}
