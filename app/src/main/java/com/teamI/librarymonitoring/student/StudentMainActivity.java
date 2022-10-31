package com.teamI.librarymonitoring.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.teamI.librarymonitoring.PrivacyActivity;
import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.SharedPreferencePrivacyUtility;
import com.teamI.librarymonitoring.librarian.LibrarianNoiseLevelActivity;
import com.teamI.librarymonitoring.librarian.LibrarianOccupancyActivity;
import com.teamI.librarymonitoring.librarian.LibrarianSensorsConnectedActivity;
import com.teamI.librarymonitoring.librarian.LibrarianSettingsActivity;

public class StudentMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
    }
}


