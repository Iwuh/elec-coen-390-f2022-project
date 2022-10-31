package com.teamI.librarymonitoring.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import com.teamI.librarymonitoring.PrivacyActivity;
import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.librarian.LibrarianMainActivity;
import com.teamI.librarymonitoring.librarian.LibrarianSettingsActivity;
import com.teamI.utils.Preferences;

public class StudentSettingsActivity extends AppCompatActivity {
    Button btnLibrarian, btncredittoconcordia_student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_settings);
        btnLibrarian = findViewById(R.id.btnChangeToLibrarian);
        btncredittoconcordia_student = findViewById(R.id.btncredittoconcordia_student);
        btnLibrarian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentSettingsActivity.this, LibrarianMainActivity.class);
                startActivity(intent);
                Preferences.writeString(StudentSettingsActivity.this,"user","librarian");
                finish();
            }
        });

        btncredittoconcordia_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

                View viewcreditwindow = layoutInflater.inflate(R.layout.creditconcordia_layout,null);

                PopupWindow popupWindow = new PopupWindow(viewcreditwindow,1000,1300,true);

                popupWindow.showAtLocation(v, Gravity.CENTER,0,0);

                viewcreditwindow.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
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

        Button btnChangeToLibrarian = findViewById(R.id.btnChangeToLibrarian);
        btnChangeToLibrarian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToLibrarianVersion();
            }
        }
        );

    }

    private void Favorites(){ }
    private void displayPrivacyAgreement(){
        Intent intent = new Intent(StudentSettingsActivity.this, PrivacyActivity.class);
        startActivity(intent);
        // TODO: display privacy agreement
        // the agreement has not been written yet
    }
    private void changeToLibrarianVersion(){}
}
