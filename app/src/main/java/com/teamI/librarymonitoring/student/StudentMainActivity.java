package com.teamI.librarymonitoring.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.teamI.librarymonitoring.HoursActivity;
import com.teamI.librarymonitoring.PrivacyActivity;
import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.SharedPreferenceUtility;

public class StudentMainActivity extends AppCompatActivity {

    protected Button btnSettings;
    protected Button btnOccupancy;
    protected Button btnNoiseLevel;
    protected Button btnFavorites;
    protected Button btnHours;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);

        boolean bHasAgreedToPrivacy = SharedPreferenceUtility.getPrivacyConsent(this);
        if(!bHasAgreedToPrivacy){
            startPrivacyActivity();
            // the privacy activity closes the app if the user does not consent
        }

        btnSettings = (Button) findViewById(R.id.btnSettings);
        btnOccupancy = (Button) findViewById(R.id.btnOccupancy);
        btnNoiseLevel = (Button) findViewById(R.id.btnNoiseLevel);
        btnFavorites = (Button) findViewById(R.id.btnFavorites);
        btnHours = (Button) findViewById(R.id.btnHours);

        setupButtons();

    }
    private void setupButtons() {
        btnNoiseLevel.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 startActivity(new Intent(StudentMainActivity.this, StudentNoiseLevelActivity.class));
                                             }
                                         }
        );
        btnOccupancy.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                startActivity(new Intent(StudentMainActivity.this, StudentOccupancyActivity.class));
                                            }
                                        }
        );

        btnSettings.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               startActivity(new Intent(StudentMainActivity.this, StudentSettingsActivity.class));

                                           }
                                       }
        );
        btnFavorites.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                startActivity(new Intent(StudentMainActivity.this, StudentFavoritesActivity.class));
                                            }
                                        }
        );
        btnHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentMainActivity.this, HoursActivity.class));
            }
        });
    }

        private void startPrivacyActivity(){
            startActivity(new Intent(StudentMainActivity.this, PrivacyActivity.class));
        }
    }