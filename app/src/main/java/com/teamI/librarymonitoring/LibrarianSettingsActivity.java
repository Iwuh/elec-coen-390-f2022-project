package com.teamI.librarymonitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LibrarianSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_librarian_settings);

        setupButtons();
        setupLabels();
    }

    private void setupButtons(){
        Button btnChangeToStudent = findViewById(R.id.btnChangeToStudent);
        btnChangeToStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  changeToStudentVersion();
            }
        }
        );

        Button btnDisplayPrivacyAgreement = findViewById(R.id.btnDisplayPrivacyAgreement);
        btnDisplayPrivacyAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayPrivacyAgreement();
            }
        });

    }

    private void changeToStudentVersion(){
        // TODO: switch to the student version of the app
        // these screens have not been created yet
    }

    private void displayPrivacyAgreement(){
        // TODO: display privacy agreement
        // the agreement has not been written yet
    }

    private void setupLabels(){
        TextView textViewNbrOccupancySensors = findViewById(R.id.textViewNbrOccupancySensors);
        textViewNbrOccupancySensors.setText(String.valueOf(getNumberOfOccupancySensors()));
        TextView textViewNbrNoiseSensors = findViewById(R.id.textViewNbrNoiseSensors);
        textViewNbrNoiseSensors.setText(String.valueOf(getNumberOfNoiseSensors()));
    }

    private int getNumberOfNoiseSensors() {
        // TODO: get actual number of noise sensors
        return 0;
    }

    private int getNumberOfOccupancySensors() {
        // TODO: get actual number of occupancy sensors
        return 0;
    }


}