package com.teamI.librarymonitoring.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.teamI.librarymonitoring.PrivacyActivity;
import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.librarian.LibrarianMainActivity;
import com.teamI.librarymonitoring.librarian.LibrarianSettingsActivity;
import com.teamI.utils.Preferences;

public class StudentSettingsActivity extends AppCompatActivity {
    Button btnLibrarian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_settings);
        btnLibrarian = findViewById(R.id.btnChangeToLibrarian);
        btnLibrarian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentSettingsActivity.this, LibrarianMainActivity.class);
                startActivity(intent);
                Preferences.writeString(StudentSettingsActivity.this,"user","librarian");
                finish();
            }
        });
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
    }
    private void Favorites(){

    }
    private void displayPrivacyAgreement(){
        Intent intent = new Intent(StudentSettingsActivity.this, PrivacyActivity.class);
        startActivity(intent);
        // TODO: display privacy agreement
        // the agreement has not been written yet
    }
}
