package com.teamI.librarymonitoring.librarian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.teamI.librarymonitoring.CreditActivity;
import com.teamI.librarymonitoring.PrivacyActivity;
import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.SharedPreferenceUtility;
import com.teamI.librarymonitoring.student.StudentMainActivity;
import com.teamI.librarymonitoring.student.StudentSettingsActivity;
import com.teamI.utils.Preferences;

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

        Button btnconcordiacredit = findViewById(R.id.btnconcordiacredit_librarian);
        btnconcordiacredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibrarianSettingsActivity.this, CreditActivity.class);
                startActivity(intent);


            }
        });

    }

    private void changeToStudentVersion(){
        SharedPreferenceUtility.setIsStudent(this);
        Intent intent = new Intent(LibrarianSettingsActivity.this, StudentMainActivity.class);
        startActivity(intent);
        finish();
    }

    private void displayPrivacyAgreement(){
        Intent intent = new Intent(LibrarianSettingsActivity.this, PrivacyActivity.class);
        startActivity(intent);
    }

    private void setupLabels(){
        TextView textViewNbrOccupancySensors = findViewById(R.id.textViewNbrOccupancySensors);
        textViewNbrOccupancySensors.setText(String.valueOf(getNumberOfOccupancySensors()));
        TextView textViewNbrNoiseSensors = findViewById(R.id.textViewNbrNoiseSensors);
        textViewNbrNoiseSensors.setText(String.valueOf(getNumberOfNoiseSensors()));
    }

    private int getNumberOfNoiseSensors() {
        // TODO: get actual number of noise sensors
        return 1;
    }

    private int getNumberOfOccupancySensors() {
        // TODO: get actual number of occupancy sensors
        return 1;
    }


}