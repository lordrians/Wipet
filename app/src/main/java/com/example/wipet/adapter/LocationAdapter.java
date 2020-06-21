package com.example.wipet.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationHolder> {

    @NonNull
    @Override
    public LocationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull LocationHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class LocationHolder extends RecyclerView.ViewHolder {
        public LocationHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
