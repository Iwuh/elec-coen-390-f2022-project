package com.teamI.librarymonitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Sensors_ConnectedActivity extends AppCompatActivity {

    TextView sensor_1_CS, sensor_2_CS, sensor_3_CS, sensor_4_CS;
    TextView sensor1_location, sensor2_location, sensor3_location, sensor4_location;
    Button btnbackfromCS, add_sensor, delete_sensor;
    String sensor_1_location, sensor_2_location, sensor_3_location, sensor_4_location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors_connected);

        // Buttons
        btnbackfromCS = findViewById(R.id.btnbackfromCS);
        add_sensor = findViewById(R.id.btnAddSensor);
        delete_sensor = findViewById(R.id.btnDeleteSensor);

        // TextView for Name of Each Sensor
        sensor_1_CS = findViewById(R.id.sensor_1_CS_textView);
        sensor_2_CS = findViewById(R.id.sensor_2_CS_textView);
        sensor_3_CS = findViewById(R.id.sensor_3_CS_textView);
        sensor_4_CS = findViewById(R.id.sensor_4_CS_textView);

        // TextView for displaying the Location of each Sensor
        sensor1_location = findViewById(R.id.sensor1_location);
        sensor2_location = findViewById(R.id.sensor2_location);
        sensor3_location = findViewById(R.id.sensor3_location);
        sensor4_location = findViewById(R.id.sensor4_location);

        // Changing the Decibel values based on sensor readings (For now, we will add dummy values)
        sensor_1_location = "Floor 1";
        sensor_2_location = "Floor 4";
        sensor_3_location = "Floor 6";
        sensor_4_location = "Floor 10";

        sensor1_location.setText(sensor_1_location);
        sensor2_location.setText(sensor_2_location);
        sensor3_location.setText(sensor_3_location);
        sensor4_location.setText(sensor_4_location);

        // Go back to Main Activity

        btnbackfromCS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Sensors_ConnectedActivity.this, MainActivity.class));
            }
        });
    }
}