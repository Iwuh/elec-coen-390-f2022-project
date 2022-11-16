package com.teamI.librarymonitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.teamI.librarymonitoring.student.StudentMainActivity;

import java.util.ArrayList;

public class RegistrationActivity extends AppCompatActivity {

    EditText firstname_edittext,lastname_edittext,email_register_edittext,password_register_edittext;
    Button registerbtn;
    ArrayList<String> emails,passwrods;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firstname_edittext = findViewById(R.id.firstname_edittext);
        lastname_edittext = findViewById(R.id.lastname_edittext);
        email_register_edittext = findViewById(R.id.email_register_edittext);
        password_register_edittext = findViewById(R.id.password_register_edittext);
        registerbtn = findViewById(R.id.registerbtn);

        emails = new ArrayList<>();
        passwrods = new ArrayList<>();

        emails.add(email_register_edittext.getText().toString());
        passwrods.add(password_register_edittext.getText().toString());

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegistrationActivity.this, StudentMainActivity.class);
                startActivity(intent);

            }
        });

    }
}