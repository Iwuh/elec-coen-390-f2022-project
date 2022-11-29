package com.teamI.librarymonitoring.student;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.teamI.librarymonitoring.HoursActivity;
import com.teamI.librarymonitoring.IOpenDataResponseListener;
import com.teamI.librarymonitoring.OpenDataApiHelper;
import com.teamI.librarymonitoring.PrivacyActivity;
import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.SharedPreferenceUtility;
import com.teamI.librarymonitoring.datacontainers.ServiceHours;

import java.util.ArrayList;
import java.util.List;

public class StudentMainActivity extends AppCompatActivity {

    protected Button btnSettings;
    protected Button btnOccupancy;
    protected Button btnNoiseLevel;
    protected Button btnFavorites;
    protected Button btnHours, btnComputerUse;
    TextView libraryhours;
    private List<ServiceHours> allServiceHours;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        // for testing purposes

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
        btnComputerUse = findViewById(R.id.btnComputerUse);
        libraryhours = findViewById(R.id.libraryhours_textview);

        allServiceHours = new ArrayList<ServiceHours>();
        OpenDataApiHelper openDataApiHelper = new OpenDataApiHelper(this);
        openDataApiHelper.getHours(allServiceHours, new IOpenDataResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(StudentMainActivity.this, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse() {
                //System.out.println(allServiceHours.get(0).getService());
                libraryhours.setText(allServiceHours.get(0).getService() + ": " + allServiceHours.get(0).getHoursText() + "\n"
                        + allServiceHours.get(5).getService() + ": " + allServiceHours.get(5).getHoursText() + "\n"
                        + allServiceHours.get(7).getService() + ": " + allServiceHours.get(7).getHoursText());
            }
        });

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
        btnComputerUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentMainActivity.this, ComputerUseActivity.class));
            }
        });
    }

        private void startPrivacyActivity(){
            startActivity(new Intent(StudentMainActivity.this, PrivacyActivity.class));
        }
    }