package com.teamI.librarymonitoring.roledivision;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.teamI.librarymonitoring.HoursActivity;
import com.teamI.librarymonitoring.IOpenDataResponseListener;
import com.teamI.librarymonitoring.LoginActivity;
import com.teamI.librarymonitoring.OpenDataApiHelper;
import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.RoleEnum;
import com.teamI.librarymonitoring.SharedPreferenceUtility;
import com.teamI.librarymonitoring.datacontainers.ServiceHours;
import com.teamI.librarymonitoring.librarian.LibrarianMainActivity;
import com.teamI.librarymonitoring.student.StudentMainActivity;

import java.util.ArrayList;
import java.util.List;

public class RoleChoiceActivity extends AppCompatActivity {
    Button btnLibrarian, btnStudent;
    private List<ServiceHours> allServiceHours;

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

                Intent intent = new Intent(RoleChoiceActivity.this, LoginActivity.class);
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