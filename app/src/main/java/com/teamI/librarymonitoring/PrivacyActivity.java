package com.teamI.librarymonitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.teamI.utils.Preferences;

public class PrivacyActivity extends AppCompatActivity {

    protected Button btnAgree;
    protected Button btnDisagree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        btnAgree = findViewById(R.id.btnAgree);
        btnDisagree = findViewById(R.id.btnDisagree);

        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPrivacyConsent(true);
                Preferences.writeString(PrivacyActivity.this,"privacy","agree");
                finish();
            }
        });
        btnDisagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPrivacyConsent(false);
                Preferences.writeString(PrivacyActivity.this,"privacy","disagree");

                // close the app
                finishAffinity();
            }
        });
    }

    private void setPrivacyConsent(boolean bConsent){
        SharedPreferencePrivacyUtility.setPrivacyConsent(this, bConsent);
    }

}