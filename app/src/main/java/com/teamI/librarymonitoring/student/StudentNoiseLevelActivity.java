package com.teamI.librarymonitoring.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.SensorReadingRecyclerViewAdapter;
import com.teamI.librarymonitoring.datacontainers.SensorReading;

import java.util.ArrayList;
import java.util.List;

public class StudentNoiseLevelActivity extends AppCompatActivity {

    protected RecyclerView readings_noise_student_RecyclerView;
    protected SensorReadingRecyclerViewAdapter sensorReadingRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_noise_level);

        populateRecyclerView();
    }

    protected void populateRecyclerView(){
        List<SensorReading> lstSensorReadings = getSensorReadings();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        sensorReadingRecyclerViewAdapter = new SensorReadingRecyclerViewAdapter(lstSensorReadings);
        readings_noise_student_RecyclerView = findViewById(R.id.readingsNoiseStudentRecyclerView);
        readings_noise_student_RecyclerView.setLayoutManager(llm);
        readings_noise_student_RecyclerView.setAdapter(sensorReadingRecyclerViewAdapter);

        readings_noise_student_RecyclerView.addItemDecoration(new DividerItemDecoration(readings_noise_student_RecyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }

    private List<SensorReading> getSensorReadings(){
        // TODO: get real sensor readings here
        // for now, populate a list of dummy sensor readings
        List<SensorReading> lstSensorReadings = new ArrayList<SensorReading>();
        lstSensorReadings.add(new SensorReading("Toronto Reading Room", "10", "dB"));
        lstSensorReadings.add(new SensorReading("Edmonton Reading Room", "100", "dB"));
        lstSensorReadings.add(new SensorReading("Ottawa Reading Room", "43", "dB"));
        lstSensorReadings.add(new SensorReading("Calgary Reading Room", "21", "dB"));
        lstSensorReadings.add(new SensorReading("Vancouver Reading Room", "69", "dB"));
        lstSensorReadings.add(new SensorReading("Montreal Reading Room", "62", "dB"));
        lstSensorReadings.add(new SensorReading("Moncton Reading Room", "90", "dB"));
        lstSensorReadings.add(new SensorReading("Regina Reading Room", "5", "dB"));
        return lstSensorReadings;
    }
}