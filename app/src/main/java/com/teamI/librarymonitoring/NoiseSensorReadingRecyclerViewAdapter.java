package com.teamI.librarymonitoring;

// use this to populate recycler views in the occupancy and noise level screens

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;


import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teamI.librarymonitoring.datacontainers.NoiseLevelEnum;
import com.teamI.librarymonitoring.datacontainers.NoiseSensorReading;
import com.teamI.librarymonitoring.datacontainers.OccupancySensorReading;

import java.util.List;

public class NoiseSensorReadingRecyclerViewAdapter extends RecyclerView.Adapter<NoiseSensorReadingRecyclerViewAdapter.ViewHolder>{

    private static int deepGreen = Color.parseColor("#0AA900");
    private static int deepRed = Color.parseColor("#A92100");
    private static int lightGreen = Color.parseColor("#82D304");
    private static int yellow = Color.parseColor("#E3C805");

    List<NoiseSensorReading> lstNoiseSensorReadings;
    public NoiseSensorReadingRecyclerViewAdapter(List<NoiseSensorReading> lstNoiseSensorReadings){
        this.lstNoiseSensorReadings = lstNoiseSensorReadings;
    }

    @NonNull
    @Override
    public NoiseSensorReadingRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sensor_reading_recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoiseSensorReadingRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.getTextViewSensorLocation().setText(lstNoiseSensorReadings.get(position).getLocation());
        holder.getTextViewSensorMeasurement().setText(lstNoiseSensorReadings.get(position).getNoiseLevel().toString());

        // set color of view
        if(lstNoiseSensorReadings.get(position).getNoiseLevel() == NoiseLevelEnum.Silent){
            holder.getTextViewSensorMeasurement().setTextColor(deepGreen);
        }
        else if(lstNoiseSensorReadings.get(position).getNoiseLevel() == NoiseLevelEnum.Quiet){
            holder.getTextViewSensorMeasurement().setTextColor(lightGreen);
        }
        else if(lstNoiseSensorReadings.get(position).getNoiseLevel() == NoiseLevelEnum.Moderate){
            holder.getTextViewSensorMeasurement().setTextColor(yellow);
        }
        else if(lstNoiseSensorReadings.get(position).getNoiseLevel() == NoiseLevelEnum.Loud){
            holder.getTextViewSensorMeasurement().setTextColor(deepRed);
        }

    }

    @Override
    public int getItemCount() {
        return lstNoiseSensorReadings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewSensorLocation;
        private TextView textViewSensorMeasurement;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            textViewSensorLocation = itemView.findViewById(R.id.textViewSensorLocation);
            textViewSensorMeasurement = itemView.findViewById(R.id.textViewSensorMeasurement);
        }

        public TextView getTextViewSensorLocation(){
            return textViewSensorLocation;
        }

        public TextView getTextViewSensorMeasurement(){
            return textViewSensorMeasurement;
        }
    }
}
