package com.teamI.librarymonitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
                finish();
            }
        });
        btnDisagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPrivacyConsent(false);

                // close the app
                finishAffinity();
            }
        });
    }

    private void setPrivacyConsent(boolean bConsent){
        SharedPreferenceUtility.setPrivacyConsent(this, bConsent);
    }

}