package com.teamI.librarymonitoring;

// use this to populate recycler views in the occupancy and noise level screens

import android.view.LayoutInflater;
import android.view.View;


import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teamI.librarymonitoring.datacontainers.OccupancySensorReading;

import java.util.List;

public class OccupancySensorReadingRecyclerViewAdapter extends RecyclerView.Adapter<OccupancySensorReadingRecyclerViewAdapter.ViewHolder>{

    List<OccupancySensorReading> lstSensorReadings;

    public OccupancySensorReadingRecyclerViewAdapter(List<OccupancySensorReading> lstSensorReadings) {
        this.lstSensorReadings = lstSensorReadings;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewSensorLocation;
        private TextView textViewSensorMeasurement;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSensorLocation = itemView.findViewById(R.id.textViewSensorLocation);
            textViewSensorMeasurement = itemView.findViewById(R.id.textViewSensorMeasurement);
        }

        public TextView getSensorLocationTextView(){
            return textViewSensorLocation;
        }

        public TextView getSensorMeasurementTextView(){
            return textViewSensorMeasurement;
        }
    }

    @NonNull
    @Override
    public OccupancySensorReadingRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sensor_reading_recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OccupancySensorReadingRecyclerViewAdapter.ViewHolder holder, int position) {
        // display the readings from the list
        holder.getSensorMeasurementTextView().setText(lstSensorReadings.get(position).getMeasurement() + " " + lstSensorReadings.get(position).getUnit());
        holder.getSensorLocationTextView().setText(lstSensorReadings.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return lstSensorReadings.size();
    }
}
