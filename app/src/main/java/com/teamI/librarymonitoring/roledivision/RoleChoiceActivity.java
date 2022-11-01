package com.teamI.librarymonitoring.roledivision;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.RoleEnum;
import com.teamI.librarymonitoring.SharedPreferenceUtility;
import com.teamI.librarymonitoring.librarian.LibrarianMainActivity;
import com.teamI.librarymonitoring.student.StudentMainActivity;

public class RoleChoiceActivity extends AppCompatActivity {
    Button btnLibrarian, btnStudent;
    TextView textViewConcordiaHours;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_choice);
        btnLibrarian = findViewById(R.id.btnLibrarian);
        btnStudent = findViewById(R.id.btnStudent);

        RoleEnum role = SharedPreferenceUtility.getRole(this);
        // if role is already defined, leave the activity and go directly to main activity
        if (role == RoleEnum.Librarian){
            Intent intent = new Intent(RoleChoiceActivity.this, LibrarianMainActivity.class);
            startActivity(intent);
            finish();
        }
        if (role == RoleEnum.Student){
            Intent intent = new Intent(RoleChoiceActivity.this, StudentMainActivity.class);
            startActivity(intent);
            finish();
        }
        btnLibrarian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferenceUtility.setIsLibrarian(RoleChoiceActivity.this);
                Intent intent = new Intent(RoleChoiceActivity.this, LibrarianMainActivity.class);
                startActivity(intent);
                finish();

            }
        });
        btnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferenceUtility.setIsStudent(RoleChoiceActivity.this);
                Intent intent = new Intent(RoleChoiceActivity.this, StudentMainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}