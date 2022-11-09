package com.teamI.librarymonitoring.splash;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.teamI.helper.FirebaseHelper;
import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.SharedPreferenceUtility;
import com.teamI.librarymonitoring.roledivision.RoleChoiceActivity;

public class Splash extends AppCompatActivity {
    FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        firebaseHelper = new FirebaseHelper();
        firebaseHelper.readData(Splash.this,"Sensors");
        Intent intent  = new Intent(this, RoleChoiceActivity.class);
        startActivity(intent);
        finish();

    }
}