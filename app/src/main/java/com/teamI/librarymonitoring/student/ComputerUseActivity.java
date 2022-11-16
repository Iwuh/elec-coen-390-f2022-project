package com.teamI.librarymonitoring.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.teamI.librarymonitoring.IOpenDataResponseListener;
import com.teamI.librarymonitoring.OpenDataApiHelper;
import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.datacontainers.LibraryComputerData;

import java.util.ArrayList;

public class ComputerUseActivity extends AppCompatActivity {

    ArrayList<LibraryComputerData> lstLibraryComputerData;
    protected TextView textViewLaptopsWebster;
    protected TextView textViewTabletsWebster;
    protected TextView textViewDesktopsWebster;
    protected TextView textViewLaptopsVanier;
    protected TextView textViewTabletsVanier;
    protected TextView textViewDesktopsVanier;
    protected Button btnDesktopsWebster;
    protected Button btnDesktopsVanier;
    protected LibraryComputerData websterLCData;
    protected LibraryComputerData vanierLCData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer_use);
        lstLibraryComputerData = new ArrayList<LibraryComputerData>();

        initializeTextViews();

        Handler volleyRequestHandler = new Handler();

        Runnable getNewComputerData = new Runnable() {
            @Override
            public void run() {
                OpenDataApiHelper openDataApiHelper = new OpenDataApiHelper(ComputerUseActivity.this);
                openDataApiHelper.getLibraryComputerData(lstLibraryComputerData, new IOpenDataResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(ComputerUseActivity.this, message, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse() {
                        populateUIWithComputerUseData();
                        initializeButtons();
                    }
                });

                volleyRequestHandler.postDelayed(this, 60000);
                // update every 60 seconds while the activity is running
            }
        };
        OpenDataApiHelper openDataApiHelper = new OpenDataApiHelper(this);
        openDataApiHelper.getLibraryComputerData(lstLibraryComputerData, new IOpenDataResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(ComputerUseActivity.this, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse() {
                populateUIWithComputerUseData();
                initializeButtons();
            }
        });

        volleyRequestHandler.post(getNewComputerData);




    }

    private void populateUIWithComputerUseData(){
        // the list should have one element for Webster and one element for Vanier
        websterLCData = new LibraryComputerData();
        vanierLCData = new LibraryComputerData();

        for(int k = 0; k < lstLibraryComputerData.size(); k++){
            if(lstLibraryComputerData.get(k).getLibraryName().equals("Webster")){
                websterLCData = lstLibraryComputerData.get(k);
            }
            if(lstLibraryComputerData.get(k).getLibraryName().equals("Vanier")){
                vanierLCData = lstLibraryComputerData.get(k);
            }
        }

        textViewTabletsWebster.setText(Integer.toString(websterLCData.getTablets()) + " tablets");
        textViewLaptopsWebster.setText(Integer.toString(websterLCData.getLaptops()) + " laptops");
        textViewDesktopsWebster.setText(Integer.toString(websterLCData.getTotalDesktops()) + " desktops");

        textViewTabletsVanier.setText(Integer.toString(vanierLCData.getTablets()) + " tablets");
        textViewLaptopsVanier.setText(Integer.toString(vanierLCData.getLaptops()) + " laptops");
        textViewDesktopsVanier.setText(Integer.toString(vanierLCData.getTotalDesktops()) + " desktops");
    }

    private void initializeTextViews(){
        textViewLaptopsWebster = findViewById(R.id.textViewLaptopsWebster);
        textViewTabletsWebster = findViewById(R.id.textViewTabletsWebster);
        textViewDesktopsWebster = findViewById(R.id.textViewDesktopsWebster);
        textViewLaptopsVanier = findViewById(R.id.textViewLaptopsVanier);
        textViewTabletsVanier = findViewById(R.id.textViewTabletsVanier);
        textViewDesktopsVanier = findViewById(R.id.textViewDesktopsVanier);
    }

    private void initializeButtons(){
        btnDesktopsVanier = findViewById(R.id.btnDesktopsVanier);
        btnDesktopsWebster = findViewById(R.id.btnDesktopsWebster);

        btnDesktopsWebster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ComputerUseActivity.this, LibraryDesktopDetailsActivity.class);
                intent.putExtra("Library", websterLCData);
                startActivity(intent);
            }
        });
        btnDesktopsVanier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ComputerUseActivity.this, LibraryDesktopDetailsActivity.class);
                intent.putExtra("Library", vanierLCData);
                startActivity(intent);
            }
        });
    }
}