package com.teamI.librarymonitoring.librarian;

import androidx.appcompat.app.AppCompatActivity;

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
import com.teamI.librarymonitoring.splash.Splash;
import com.teamI.librarymonitoring.student.StudentMainActivity;
import com.teamI.librarymonitoring.student.StudentSettingsActivity;

public class LibrarianMainActivity extends AppCompatActivity {

    protected Button btnSettings;
    protected Button btnOccupancy;
    protected Button btnNoiseLevel;
    protected Button btnSensorsConnected;
    protected Button btnHours;
    FirebaseHelper firebaseHelper;


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

    }


    private void startPrivacyActivity(){
        startActivity(new Intent(LibrarianMainActivity.this, PrivacyActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if(menuItem.getItemId() == R.id.action_settings){
            startActivity(new Intent(LibrarianMainActivity.this, LibrarianSettingsActivity.class));
        }
        return super.onOptionsItemSelected(menuItem);
    }
}