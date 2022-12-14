package com.teamI.librarymonitoring.librarian;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.teamI.helper.FirebaseHelper;
import com.teamI.librarymonitoring.HoursActivity;
import com.teamI.librarymonitoring.PrivacyActivity;
import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.SharedPreferenceUtility;


public class LibrarianMainActivity extends AppCompatActivity{

    protected Button btnSettings;
    protected Button btnOccupancy;
    protected Button btnNoiseLevel;
    protected Button btnSensorsConnected;
    protected Button btnHours, btnAnnouncements;
    FirebaseHelper firebaseHelper;
    protected String announcement;
    protected String announcement_timestamp;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_librarian_main);
        firebaseHelper = new FirebaseHelper();



        firebaseHelper.Noise_sensor1(LibrarianMainActivity.this,"Sensors");
        firebaseHelper.Noise_sensor2(LibrarianMainActivity.this,"Sensors");
        firebaseHelper.Noise_sensor3(LibrarianMainActivity.this,"Sensors");
        firebaseHelper.Noise_sensor4(LibrarianMainActivity.this,"Sensors");
        firebaseHelper.Occ_sensor1(LibrarianMainActivity.this,"Sensors");
        firebaseHelper.Occ_sensor2(LibrarianMainActivity.this,"Sensors");
        firebaseHelper.Occ_sensor3(LibrarianMainActivity.this,"Sensors");
        firebaseHelper.Occ_sensor4(LibrarianMainActivity.this,"Sensors");
        firebaseHelper.Occ_sensor5(LibrarianMainActivity.this,"Sensors");

        boolean bHasAgreedToPrivacy = SharedPreferenceUtility.getPrivacyConsent(this);
        if(!bHasAgreedToPrivacy){
            startPrivacyActivity();
            // the privacy activity closes the app if the user does not consent
        }


        btnOccupancy = (Button) findViewById(R.id.btnOccupancy);
        btnNoiseLevel = (Button) findViewById(R.id.btnNoiseLevel);
        btnSensorsConnected = (Button) findViewById(R.id.btnSensorsConnected);
        btnHours = (Button) findViewById(R.id.btnHours);
        btnAnnouncements = (Button) findViewById(R.id.btnAnnouncements);

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

        btnAnnouncements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AnnouncementsFragment announcementsFragment = new AnnouncementsFragment();
                announcementsFragment.show(getSupportFragmentManager(), "Add Announcements");



            }
        });


    }


    private void startPrivacyActivity(){
        startActivity(new Intent(LibrarianMainActivity.this, PrivacyActivity.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.librarian_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if(menuItem.getItemId() == R.id.action_settings_librarian){
            startActivity(new Intent(LibrarianMainActivity.this, LibrarianSettingsActivity.class));
        }
        else if(menuItem.getItemId() == R.id.sentannouncements){
            goToAnnouncementActivity();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void goToAnnouncementActivity() {
        Intent intent = new Intent(LibrarianMainActivity.this, AnnouncementsActivity.class);
        startActivity(intent);
    }

}