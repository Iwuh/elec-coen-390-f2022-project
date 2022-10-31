package com.teamI.librarymonitoring.mainScreen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.teamI.librarymonitoring.PrivacyActivity;
import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.librarian.LibrarianMainActivity;
import com.teamI.librarymonitoring.student.StudentMainActivity;
import com.teamI.librarymonitoring.student.StudentSettingsActivity;
import com.teamI.utils.Preferences;

public class MainActivity extends AppCompatActivity {
    Button library, student;
    TextView concordia_hours;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        library = findViewById(R.id.lib);
        student = findViewById(R.id.student);
        concordia_hours = findViewById(R.id.concordiahourstextView);
        if (Preferences.readString(MainActivity.this,"user").equals("librarian")){
            Intent intent = new Intent(MainActivity.this, LibrarianMainActivity.class);
            startActivity(intent);
        }
        if (Preferences.readString(MainActivity.this,"user").equals("student")){
            Intent intent = new Intent(MainActivity.this, StudentMainActivity.class);
            startActivity(intent);
        }
        library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Preferences.readString(MainActivity.this,"privacy").equals("agree")){
                    Intent intent = new Intent(MainActivity.this, LibrarianMainActivity.class);
                    startActivity(intent);
                    Preferences.writeString(MainActivity.this,"user","librarian");
                    finish();
                }
                else {
                    Intent intent = new Intent(MainActivity.this, PrivacyActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Preferences.readString(MainActivity.this,"privacy").equals("agree")){
                    Intent intent = new Intent(MainActivity.this, StudentMainActivity.class);
                    startActivity(intent);
                    Preferences.writeString(MainActivity.this,"user","student");
                    finish();
                }
                else {
                    Intent intent = new Intent(MainActivity.this, PrivacyActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
        });
    }
}