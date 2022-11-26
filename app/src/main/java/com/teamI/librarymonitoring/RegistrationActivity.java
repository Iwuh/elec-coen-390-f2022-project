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
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamI.librarymonitoring.student.StudentMainActivity;
import com.teamI.model.Model;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class RegistrationActivity extends AppCompatActivity {

    EditText firstname_edittext,lastname_edittext,email_register_edittext,password_register_edittext;
    Button registerbtn;
    ArrayList<String> emails,passwrods;
    private FirebaseAuth mAuth;
    Button loginBtn;
    FirebaseUser currentUser;
    ProgressDialog mProgressDialogue;
    DatabaseReference pushUser;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        firstname_edittext = findViewById(R.id.firstname_edittext);
        lastname_edittext = findViewById(R.id.lastname_edittext);
        email_register_edittext = findViewById(R.id.email_register_edittext);
        password_register_edittext = findViewById(R.id.password_register_edittext);
        registerbtn = findViewById(R.id.registerbtn);
        mProgressDialogue = new ProgressDialog(this);
        emails = new ArrayList<>();
        passwrods = new ArrayList<>();

        emails.add(email_register_edittext.getText().toString());
        passwrods.add(password_register_edittext.getText().toString());
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(email_register_edittext.getText().toString())) {
                    email_register_edittext.setError("This field must be filled");
                    return;
                }
                if (TextUtils.isEmpty(password_register_edittext.getText().toString())) {
                    password_register_edittext.setError("This field must be filled");
                    return;
                }
                mProgressDialogue.setTitle("Creating Account");
                mProgressDialogue.setMessage("Please wait while we are processing your information");
                mProgressDialogue.setCanceledOnTouchOutside(false);
                mProgressDialogue.show();
                try{
                    registerUser(email_register_edittext.getText().toString(),password_register_edittext.getText().toString());
                }
                catch(Exception ex){
                    Toast.makeText(RegistrationActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                    password_register_edittext.setText("");
                }



            }


        });



    }
    private void registerUser(String email, String password) throws Exception{

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                try{
                    if (task.isSuccessful()){
                        String rep = email.replace(".","");
                        pushUser = FirebaseDatabase.getInstance().getReference().child("users").child(rep);
                        pushUser.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Model model = new Model (firstname_edittext.getText().toString(),lastname_edittext.getText().toString(),email,password);
                                pushUser.setValue(model);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                        Toast.makeText(RegistrationActivity.this,"Registration successful", Toast.LENGTH_LONG).show();
                        mProgressDialogue.dismiss();
                        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                        startActivity(intent);
                        FirebaseUser user = mAuth.getCurrentUser();
                    }
                    else {
                       throw new Exception(task.getResult().toString());
                    }
                    }
                    catch(Exception ex){
                        // get a readable message to show
                        String fullMessage = ex.getMessage();
                        int indexToStartAt = fullMessage.indexOf(":") + 2;
                        String messageWithStartCut = fullMessage.substring(indexToStartAt);
                        mProgressDialogue.dismiss();
                        Toast.makeText(RegistrationActivity.this, messageWithStartCut, Toast.LENGTH_LONG).show();
                        password_register_edittext.setText("");
                    }


}

        });

    }
}