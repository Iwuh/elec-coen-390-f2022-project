package com.teamI.librarymonitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSettings = (Button) findViewById(R.id.btnSettings);
        Button btnOccupancy = (Button) findViewById(R.id.btnOccupancy); // this is a comment in the test branch
        Button btnNoiseLevel = (Button) findViewById(R.id.btnNoiseLevel);

        btnNoiseLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NoiseLevelActivity.class));
            }
        }
        );
        btnOccupancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, OccupancyActivity.class));
            }
        }
        );
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        }
        );
    }
}