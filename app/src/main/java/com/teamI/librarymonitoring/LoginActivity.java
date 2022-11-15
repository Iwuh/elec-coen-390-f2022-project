package com.teamI.librarymonitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.teamI.librarymonitoring.student.StudentMainActivity;

public class LoginActivity extends AppCompatActivity {

    EditText email_edittext,password_login_edittext;
    Button btnlogin,createaccountbtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_edittext = findViewById(R.id.email_edittext);
        password_login_edittext = findViewById(R.id.password_login_edittext);
        btnlogin = findViewById(R.id.btnlogin);
        createaccountbtn = findViewById(R.id.createaccountbtn);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,StudentMainActivity.class);
                startActivity(intent);
            }
        });

        createaccountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}