package com.teamI.librarymonitoring.student;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.librarian.LibrarianMainActivity;
import com.teamI.librarymonitoring.librarian.LibrarianNoiseLevelActivity;

public class StudentMainActivity extends AppCompatActivity {

    Button Btnfavorites;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);

        Btnfavorites = findViewById(R.id.Btnfavorites);

        Btnfavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(StudentMainActivity.this, FavoritesActivity.class));
            }
        });
    }
}