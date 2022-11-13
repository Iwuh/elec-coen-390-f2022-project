package com.teamI.librarymonitoring.librarian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.teamI.librarymonitoring.HoursActivity;
import com.teamI.librarymonitoring.PrivacyActivity;
import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.SharedPreferenceUtility;

public class LibrarianMainActivity extends AppCompatActivity {

    protected Button btnSettings;
    protected Button btnOccupancy;
    protected Button btnNoiseLevel;
    protected Button btnSensorsConnected;
    protected Button btnHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_librarian_main);


        boolean bHasAgreedToPrivacy = SharedPreferenceUtility.getPrivacyConsent(this);
        if(!bHasAgreedToPrivacy){
            startPrivacyActivity();
            // the privacy activity closes the app if the user does not consent
        }


        btnSettings = (Button) findViewById(R.id.btnSettings);
        btnOccupancy = (Button) findViewById(R.id.btnOccupancy);
        btnNoiseLevel = (Button) findViewById(R.id.btnNoiseLevel);
        btnSensorsConnected = (Button) findViewById(R.id.btnSensorsConnected);
        btnHours = (Button) findViewById(R.id.btnHours);

        setupButtons();


    }

    private void setupButtons(){
        btnNoiseLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LibrarianMainActivity.this, LibrarianNoiseLevelActivity.class));



            }
        }
        );
        btnOccupancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LibrarianMainActivity.this, LibrarianOccupancyActivity.class));
            }
        }
        );
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LibrarianMainActivity.this, LibrarianSettingsActivity.class));

            }
        }
        );

        btnSensorsConnected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LibrarianMainActivity.this, LibrarianSensorsConnectedActivity.class));
            }
        });

        btnHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LibrarianMainActivity.this, HoursActivity.class));
            }
        });

    }


    private void startPrivacyActivity(){
        startActivity(new Intent(LibrarianMainActivity.this, PrivacyActivity.class));
    }
}