package com.teamI.librarymonitoring.student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.teamI.librarymonitoring.R;

public class Rules_and_RegulationActivity extends AppCompatActivity {

    TextView rulesandregulation_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_and_regulation);

        rulesandregulation_textview = findViewById(R.id.rulesandregulation_textview);
        rulesandregulation_textview.setMovementMethod(new ScrollingMovementMethod());
    }
}