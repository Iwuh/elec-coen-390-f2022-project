package com.teamI.librarymonitoring.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.SensorReadingRecyclerViewAdapter;
import com.teamI.librarymonitoring.datacontainers.SensorReading;

import java.util.ArrayList;
import java.util.List;

public class StudentOccupancyActivity extends AppCompatActivity {

    protected RecyclerView readingsOccStudentRecyclerView;
    protected SensorReadingRecyclerViewAdapter sensorReadingRecyclerViewAdapter;
    protected Button btnTotalOccupancy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_occupancy);
        btnTotalOccupancy = findViewById(R.id.btnTotalOccupancy);
        btnTotalOccupancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentOccupancyActivity.this, TotalOccupancyActivity.class);
                startActivity(intent);
            }
        });

        populateRecyclerView();
    }

    protected void populateRecyclerView(){
        List<SensorReading> lstSensorReadings = getSensorReadings();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        sensorReadingRecyclerViewAdapter = new SensorReadingRecyclerViewAdapter(lstSensorReadings);
        readingsOccStudentRecyclerView = findViewById(R.id.readingsOccStudentRecyclerView);
        readingsOccStudentRecyclerView.setLayoutManager(llm);
        readingsOccStudentRecyclerView.setAdapter(sensorReadingRecyclerViewAdapter);

        readingsOccStudentRecyclerView.addItemDecoration(new DividerItemDecoration(readingsOccStudentRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
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