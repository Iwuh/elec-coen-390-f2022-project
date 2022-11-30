package com.teamI.librarymonitoring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.teamI.librarymonitoring.librarian.LibrarianMainActivity;
import com.teamI.librarymonitoring.roledivision.RoleChoiceActivity;
import com.teamI.librarymonitoring.student.StudentMainActivity;

public class LoginActivity extends AppCompatActivity {

    EditText email_edittext,password_login_edittext;
    Button btnlogin,createaccountbtn;
    private FirebaseAuth mAuth;
    Button loginBtn;
    FirebaseUser currentUser;
    ProgressDialog mProgressDialogue;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_edittext = findViewById(R.id.email_edittext);
        password_login_edittext = findViewById(R.id.password_login_edittext);
        btnlogin = findViewById(R.id.btnlogin);
        createaccountbtn = findViewById(R.id.createaccountbtn);
        mProgressDialogue = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(email_edittext.getText().toString())) {
                    email_edittext.setError("This field must be filled");
                    return;
                }
                if (TextUtils.isEmpty(password_login_edittext.getText().toString())) {
                    password_login_edittext.setError("This field must be filled");
                    return;
                }
                mProgressDialogue.setTitle("Creating Account");
                mProgressDialogue.setMessage("Please wait while we are processing your information");
                mProgressDialogue.setCanceledOnTouchOutside(false);
                mProgressDialogue.show();
                mAuth.signInWithEmailAndPassword(email_edittext.getText().toString(), password_login_edittext.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()) {
                            SharedPreferenceUtility.setIsLibrarian(LoginActivity.this);
                            mProgressDialogue.dismiss();

                            Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, LibrarianMainActivity.class);
                            startActivity(intent);                        }
                        else {
                            mProgressDialogue.dismiss();


                            Toast.makeText(LoginActivity.this, "An error occurred!" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

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