package com.teamI.librarymonitoring.student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.teamI.librarymonitoring.R;

public class StudentSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_settings);

        setupButtons();

    }
    private void setupButtons() {

        Button btnFavorites = findViewById(R.id.btnFavorites);
        btnFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Favorites();
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

    private void Favorites(){ }
    private void displayPrivacyAgreement(){
        // TODO: display privacy agreement
        // the agreement has not been written yet
    }
    private void changeToLibrarianVersion(){}
}
