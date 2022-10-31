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

import com.teamI.librarymonitoring.PrivacyActivity;
import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.mainScreen.MainActivity;
import com.teamI.librarymonitoring.student.StudentMainActivity;
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

                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

                View viewcreditwindow = layoutInflater.inflate(R.layout.creditconcordia_layout,null);

                PopupWindow popupWindow = new PopupWindow(viewcreditwindow,1000,1300,true);

                popupWindow.showAtLocation(v, Gravity.CENTER,0,0);

                viewcreditwindow.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });


            }
        });

    }

    private void changeToStudentVersion(){
        // TODO: switch to the student version of the app
        Intent intent = new Intent(LibrarianSettingsActivity.this, StudentMainActivity.class);
        startActivity(intent);
        Preferences.writeString(LibrarianSettingsActivity.this,"user","student");
        finish();
        // these screens have not been created yet
    }

    private void displayPrivacyAgreement(){
        // TODO: display privacy agreement
        Intent intent = new Intent(LibrarianSettingsActivity.this, PrivacyActivity.class);
        startActivity(intent);
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