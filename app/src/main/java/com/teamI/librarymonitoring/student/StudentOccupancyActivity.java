package com.teamI.librarymonitoring.student;

import static com.teamI.helper.FirebaseHelper.CalgaryO;
import static com.teamI.helper.FirebaseHelper.EdmontonO;
import static com.teamI.helper.FirebaseHelper.OttawaO;
import static com.teamI.helper.FirebaseHelper.TorontoO;
import static com.teamI.helper.FirebaseHelper.VancouverO;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import com.teamI.helper.FirebaseHelper;
import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.SensorReadingRecyclerViewAdapter;
import com.teamI.librarymonitoring.datacontainers.SensorReading;

import java.util.ArrayList;
import java.util.List;

public class StudentOccupancyActivity extends AppCompatActivity {

    protected RecyclerView readingsOccStudentRecyclerView;
    protected SensorReadingRecyclerViewAdapter sensorReadingRecyclerViewAdapter;
    protected Button btnTotalOccupancy;
    protected Handler refreshHandler;
    protected final static int msBetweenUpdates = 20000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_occupancy);
        btnTotalOccupancy = findViewById(R.id.btnTotalOccupancy);
        btnTotalOccupancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentOccupancyActivity.this, TotalOccupancyActivity.class);
                startActivity(intent);
            }
        });

        refreshHandler = new Handler();
        populateRecyclerView();
    }

    @Override
    public void onStart(){
        super.onStart();
        refreshHandler.postDelayed(new RefreshRunnable(), msBetweenUpdates);
    }

    @Override
    public void onStop(){
        super.onStop();
        refreshHandler.removeCallbacksAndMessages(null);
    }

    private class RefreshRunnable implements Runnable{
        @Override
        public void run(){
            FirebaseHelper firebaseHelper = new FirebaseHelper();
            firebaseHelper.refreshAllOccupancyReadings(StudentOccupancyActivity.this);
            Parcelable recyclerViewState = readingsOccStudentRecyclerView.getLayoutManager().onSaveInstanceState();
            populateRecyclerView();
            // restore state
            readingsOccStudentRecyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
            refreshHandler.postDelayed(this, 5000);
        }
    }

    protected void populateRecyclerView(){

        List<SensorReading> lstSensorReadings = getSensorReadings();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        sensorReadingRecyclerViewAdapter = new SensorReadingRecyclerViewAdapter(lstSensorReadings);
        readingsOccStudentRecyclerView = findViewById(R.id.readingsOccStudentRecyclerView);
        readingsOccStudentRecyclerView.setLayoutManager(llm);
        readingsOccStudentRecyclerView.setAdapter(sensorReadingRecyclerViewAdapter);

        // need to remove the decoration. Else, the recyclerview keeps growing until it does not fit on the page
        if(readingsOccStudentRecyclerView.getItemDecorationCount() != 0){
            readingsOccStudentRecyclerView.removeItemDecorationAt(0);
        }
        readingsOccStudentRecyclerView.addItemDecoration(new DividerItemDecoration(readingsOccStudentRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }

    private List<SensorReading> getSensorReadings(){
        // TODO: get real sensor readings here
        // for now, populate a list of dummy sensor readings
        List<SensorReading> lstSensorReadings = new ArrayList<SensorReading>();
        lstSensorReadings.add(new SensorReading("Toronto Reading Room", ""+TorontoO, "people"));
        lstSensorReadings.add(new SensorReading("Edmonton Reading Room", ""+EdmontonO, "people"));
        lstSensorReadings.add(new SensorReading("Ottawa Reading Room", ""+OttawaO, "people"));
        lstSensorReadings.add(new SensorReading("Calgary Reading Room", ""+CalgaryO, "people"));
        lstSensorReadings.add(new SensorReading("Vancouver Reading Room", ""+VancouverO, "people"));
        lstSensorReadings.add(new SensorReading("Montreal Reading Room", "50", "people"));
        lstSensorReadings.add(new SensorReading("Moncton Reading Room", "514", "people"));
        lstSensorReadings.add(new SensorReading("Regina Reading Room", "611", "people"));
        return lstSensorReadings;
    }
}