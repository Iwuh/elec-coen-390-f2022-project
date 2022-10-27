package com.teamI.librarymonitoring.librarian;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.teamI.librarymonitoring.R;


// TODO: this activity should use a RecyclerView to display readings

public class LibrarianNoiseLevelActivity extends AppCompatActivity {

    TextView sensor_1_dB, sensor_2_dB, sensor_3_dB, sensor_4_dB;
    TextView sensor1_dB_value, sensor2_dB_value, sensor3_dB_value, sensor4_dB_value;
    double sensor1_value, sensor2_value, sensor3_value, sensor4_value;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_librarian_noise_level);

        // TextView for Name of Each Sensor
        sensor_1_dB = findViewById(R.id.sensor_1_dB_textView);
        sensor_2_dB = findViewById(R.id.sensor_2_dB_textView);
        sensor_3_dB = findViewById(R.id.sensor_3_dB_textView);
        sensor_4_dB = findViewById(R.id.sensor_4_dB_textView);

        // TextView for displaying the Decibel level of each Sensor
        sensor1_dB_value = findViewById(R.id.sensor1_dB_value);
        sensor2_dB_value = findViewById(R.id.sensor2_dB_value);
        sensor3_dB_value = findViewById(R.id.sensor3_dB_value);
        sensor4_dB_value = findViewById(R.id.sensor4_dB_value);

        // Changing the Decibel values based on sensor readings (For now, we will add dummy values)
        sensor1_value = 35.87;
        sensor2_value = 10.43;
        sensor3_value = 100.03;
        sensor4_value = 60.43;

        sensor1_dB_value.setText(sensor1_value + " dB");
        sensor2_dB_value.setText(sensor2_value + " dB");
        sensor3_dB_value.setText(sensor3_value + " dB");
        sensor4_dB_value.setText(sensor4_value + " dB");

    }
}