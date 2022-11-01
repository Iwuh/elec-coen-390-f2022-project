package com.teamI.librarymonitoring.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.teamI.librarymonitoring.CreditActivity;
import com.teamI.librarymonitoring.PrivacyActivity;
import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.SharedPreferenceUtility;
import com.teamI.librarymonitoring.librarian.LibrarianMainActivity;
import com.teamI.utils.Preferences;

public class StudentSettingsActivity extends AppCompatActivity {
    Button btnLibrarian, btncredittoconcordia_student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_settings);
        btnLibrarian = findViewById(R.id.btnChangeToLibrarian);
        btncredittoconcordia_student = findViewById(R.id.btncredittoconcordia_student);
        btnLibrarian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentSettingsActivity.this, LibrarianMainActivity.class);
                startActivity(intent);
                Preferences.writeString(StudentSettingsActivity.this,"user","librarian");
                finish();
            }
        });

        btncredittoconcordia_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(StudentSettingsActivity.this, CreditActivity.class);
               startActivity(intent);
            }
        });
        setupButtons();

    }
    private void setupButtons() {

        Button btnFavorites = findViewById(R.id.btnFavorites);
        btnFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayFavorites();
            }

        });

        Button btnDisplayPrivacyAgreement = findViewById(R.id.btnDisplayPrivacyAgreement);
        btnDisplayPrivacyAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayPrivacyAgreement();
            }
        }
        );

        Button btnChangeToLibrarian = findViewById(R.id.btnChangeToLibrarian);
        btnChangeToLibrarian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToLibrarianVersion();
            }
        }
        );

    }

    private void displayFavorites(){
        Intent intent = new Intent(StudentSettingsActivity.this, StudentFavoritesActivity.class);
        startActivity(intent);
    }

    private void displayPrivacyAgreement(){
        Intent intent = new Intent(StudentSettingsActivity.this, PrivacyActivity.class);
        startActivity(intent);
    }

    private void changeToLibrarianVersion(){
        SharedPreferenceUtility.setIsLibrarian(this);
        Intent intent = new Intent(StudentSettingsActivity.this, LibrarianMainActivity.class);
        startActivity(intent);
        finish();
    }
}
