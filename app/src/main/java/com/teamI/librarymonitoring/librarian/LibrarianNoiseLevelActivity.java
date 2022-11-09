package com.teamI.librarymonitoring.librarian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.SensorReadingRecyclerViewAdapter;
import com.teamI.librarymonitoring.datacontainers.SensorReading;

import java.util.ArrayList;
import java.util.List;


// TODO: this activity should use a RecyclerView to display readings

public class LibrarianNoiseLevelActivity extends AppCompatActivity {

    protected RecyclerView readings_noise_librarian_RecyclerView;
    protected SensorReadingRecyclerViewAdapter sensorReadingRecyclerViewAdapter;
    protected FloatingActionButton FAB_noisedetails;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_librarian_noise_level);

        populateRecyclerView();
        FAB_noisedetails = findViewById(R.id.faBtnNoiseLevelDetail);

        FAB_noisedetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayNoiseDetails();
            }
        });


    }

    protected void populateRecyclerView(){
        List<SensorReading> lstSensorReadings = getSensorReadings();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        sensorReadingRecyclerViewAdapter = new SensorReadingRecyclerViewAdapter(lstSensorReadings);
        readings_noise_librarian_RecyclerView = findViewById(R.id.readingsNoiseLibrarianRecyclerView);
        readings_noise_librarian_RecyclerView.setLayoutManager(llm);
        readings_noise_librarian_RecyclerView.setAdapter(sensorReadingRecyclerViewAdapter);

        readings_noise_librarian_RecyclerView.addItemDecoration(new DividerItemDecoration(readings_noise_librarian_RecyclerView.getContext(), DividerItemDecoration.VERTICAL));
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

    private void displayNoiseDetails(){
        // TODO: display noise details exclusive to librarian
        Toast.makeText(this, "Not implemented.", Toast.LENGTH_SHORT).show();
    }
}