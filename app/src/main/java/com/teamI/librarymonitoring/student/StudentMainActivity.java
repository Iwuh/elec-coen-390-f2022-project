package com.teamI.librarymonitoring.student;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;


import android.view.MenuInflater;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.teamI.helper.FirebaseHelper;
import com.teamI.librarymonitoring.HoursActivity;
import com.teamI.librarymonitoring.IOpenDataResponseListener;
import com.teamI.librarymonitoring.OpenDataApiHelper;
import com.teamI.librarymonitoring.PrivacyActivity;
import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.SharedPreferenceUtility;
import com.teamI.librarymonitoring.datacontainers.ServiceHours;
import com.teamI.librarymonitoring.librarian.AnnouncementsActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentMainActivity extends AppCompatActivity {


    protected Button btnstudent_announcement;

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


        boolean bHasAgreedToPrivacy = SharedPreferenceUtility.getPrivacyConsent(this);
        if(!bHasAgreedToPrivacy){
            startPrivacyActivity();
            // the privacy activity closes the app if the user does not consent
        }


        btnstudent_announcement = (Button) findViewById(R.id.btnstudent_announcement);

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


        btnstudent_announcement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentMainActivity.this, AnnouncementsActivity.class));
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

    // TODO merge student_activity_menu and menu_main
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.student_activity_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(*//*@NonNull*//* MenuItem item) {

        switch(item.getItemId()){
            case R.id.student_settings:
                startActivity(new Intent(StudentMainActivity.this, StudentSettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

        private void startPrivacyActivity(){
            startActivity(new Intent(StudentMainActivity.this, PrivacyActivity.class));
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu){
            getMenuInflater().inflate(R.menu.student_activity_menu, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem menuItem){
            if(menuItem.getItemId() == R.id.action_settings_student){
                startActivity(new Intent(StudentMainActivity.this, StudentSettingsActivity.class));
            }
            return super.onOptionsItemSelected(menuItem);
        }
    }