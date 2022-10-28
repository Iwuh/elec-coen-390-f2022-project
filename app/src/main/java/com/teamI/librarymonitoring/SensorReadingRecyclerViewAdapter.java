package com.teamI.librarymonitoring;

// use this to populate recycler views in the occupancy and noise level screens

import android.view.LayoutInflater;
import android.view.View;


import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teamI.librarymonitoring.datacontainers.SensorReading;

import java.util.List;

public class SensorReadingRecyclerViewAdapter extends RecyclerView.Adapter<SensorReadingRecyclerViewAdapter.ViewHolder>{

    List<SensorReading> lstSensorReadings;

    public SensorReadingRecyclerViewAdapter(List<SensorReading> lstSensorReadings) {
        this.lstSensorReadings = lstSensorReadings;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewSensorLocation;
        private TextView textViewSensorMeasurement;
        private TextView textViewSensorUnits;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSensorLocation = itemView.findViewById(R.id.textViewSensorLocation);
            textViewSensorMeasurement = itemView.findViewById(R.id.textViewSensorMeasurement);
            textViewSensorUnits = itemView.findViewById(R.id.textViewSensorUnits);
        }

        public TextView getSensorLocationTextView(){
            return textViewSensorLocation;
        }

        public TextView getSensorMeasurementTextView(){
            return textViewSensorMeasurement;
        }

        public TextView getSensorUnitsTextView(){
            return textViewSensorUnits;
        }
    }

    @NonNull
    @Override
    public SensorReadingRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sensor_reading_recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SensorReadingRecyclerViewAdapter.ViewHolder holder, int position) {
        // display the readings from the list
        holder.getSensorMeasurementTextView().setText(lstSensorReadings.get(position).getMeasurement());
        holder.getSensorLocationTextView().setText(lstSensorReadings.get(position).getLocation());
        holder.getSensorUnitsTextView().setText(lstSensorReadings.get(position).getUnit());
    }

    @Override
    public int getItemCount() {
        return lstSensorReadings.size();
    }
}