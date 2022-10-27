package com.teamI.librarymonitoring;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.teamI.librarymonitoring.datacontainers.SensorReading;

import java.util.ArrayList;
import java.util.List;

public class OccupancyActivity extends AppCompatActivity {

    protected RecyclerView readingsRecyclerView;
    protected SensorReadingRecyclerViewAdapter sensorReadingRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_occupancy);

        populateRecyclerView();
    }

    protected void populateRecyclerView(){
        List<SensorReading> lstSensorReadings = getSensorReadings();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        sensorReadingRecyclerViewAdapter = new SensorReadingRecyclerViewAdapter(lstSensorReadings);
        readingsRecyclerView = findViewById(R.id.readingsRecyclerView);
        readingsRecyclerView.setLayoutManager(llm);
        readingsRecyclerView.setAdapter(sensorReadingRecyclerViewAdapter);
    }


    private List<SensorReading> getSensorReadings(){
        // TODO: get real sensor readings here
        // for now, populate a list of dummy sensor readings
        List<SensorReading> lstSensorReadings = new ArrayList<SensorReading>();
        lstSensorReadings.add(new SensorReading("Toronto Reading Room", "123", "people"));
        lstSensorReadings.add(new SensorReading("Edmonton Reading Room", "456", "people"));
        lstSensorReadings.add(new SensorReading("Ottawa Reading Room", "390", "people"));
        lstSensorReadings.add(new SensorReading("Calgary Reading Room", "411", "people"));
        lstSensorReadings.add(new SensorReading("Vancouver Reading Room", "10", "people"));
        lstSensorReadings.add(new SensorReading("Montreal Reading Room", "50", "people"));
        lstSensorReadings.add(new SensorReading("Moncton Reading Room", "514", "people"));
        lstSensorReadings.add(new SensorReading("Regina Reading Room", "611", "people"));
        return lstSensorReadings;
    }
}