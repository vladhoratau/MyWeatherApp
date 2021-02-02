package com.example.myweatherapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweatherapp.R;
import com.example.myweatherapp.db.location.SavedLocation;
import com.example.myweatherapp.utils.ApplicationClass;
import com.example.myweatherapp.utils.ToastMessage;
import com.example.myweatherapp.utils.UnitsUtil;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SavedLocationAdapter extends RecyclerView.Adapter<SavedLocationAdapter.SavedLocationHolder> implements Filterable {
    private List<SavedLocation> savedLocations = new ArrayList<>();
    private List<SavedLocation> savedLocationsFull;
    private OnItemClickListener listener;

    @NonNull
    @Override
    public SavedLocationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.savedlocations_item, parent, false);
        return new SavedLocationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedLocationHolder holder, int position) {
        holder.savedLocationTemperatureAndDescription.setText(savedLocations.get(position).getTemperature().intValue()
                + UnitsUtil.getUnitMeasure(savedLocations.get(position).getUnit()) + ", "
                + savedLocations.get(position).getDescription());
        holder.savedLocationCityName.setText(savedLocations.get(position).getCityName());
        String weatherIconUrl = ApplicationClass.getInstance().getString(R.string.iconRoot)
                + savedLocations.get(position).getIcon() + ".png";
        Picasso.with(ApplicationClass.getInstance().getApplicationContext()).load(weatherIconUrl).into(holder.savedLocationIcon);
    }

    public void setSavedLocations(List<SavedLocation> savedLocations) {
        this.savedLocations = savedLocations;
        savedLocationsFull = new ArrayList<>(savedLocations);
        notifyDataSetChanged();
    }

    public SavedLocation getSavedLocationAt(int position) {
        return savedLocations.get(position);
    }

    @Override
    public int getItemCount() {
        return savedLocations.size();
    }

    @Override
    public Filter getFilter() {
        return savedLocationsFilter;
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ApplicationClass.getInstance().isNetworkConnected()) {
                        int position = getAdapterPosition();
                        if (listener != null && position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(savedLocations.get(position));
                        }
                    } else {
                        ToastMessage.showMessage("No network connection");
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(SavedLocation savedLocation);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private Filter savedLocationsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<SavedLocation> filteredSavedLocations = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredSavedLocations.addAll(savedLocationsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (SavedLocation location : savedLocationsFull) {
                    if (location.getCityName().toLowerCase().contains(filterPattern)) {
                        filteredSavedLocations.add(location);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredSavedLocations;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            savedLocations.clear();
            savedLocations.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
