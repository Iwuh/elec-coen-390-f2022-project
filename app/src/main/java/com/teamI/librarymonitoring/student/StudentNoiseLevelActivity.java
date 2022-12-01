package com.teamI.librarymonitoring.student;

import static com.teamI.helper.FirebaseHelper.CalgaryN;
import static com.teamI.helper.FirebaseHelper.EdmontonN;
import static com.teamI.helper.FirebaseHelper.OttawaN;
import static com.teamI.helper.FirebaseHelper.TorontoN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;

import com.teamI.helper.FirebaseHelper;
import com.teamI.librarymonitoring.NoiseSensorReadingRecyclerViewAdapter;
import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.OccupancySensorReadingRecyclerViewAdapter;
import com.teamI.librarymonitoring.datacontainers.NoiseSensorReading;
import com.teamI.librarymonitoring.datacontainers.OccupancySensorReading;

import java.util.ArrayList;
import java.util.List;

public class StudentNoiseLevelActivity extends AppCompatActivity {

    protected RecyclerView readings_noise_student_RecyclerView;
    protected NoiseSensorReadingRecyclerViewAdapter noiseSensorReadingRecyclerViewAdapter;
    protected Handler refreshHandler;
    protected static final int msBetweenUpdates = 5000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_noise_level);

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
            firebaseHelper.refreshAllNoiseLevelReadings(StudentNoiseLevelActivity.this);
            Parcelable recyclerViewState = readings_noise_student_RecyclerView.getLayoutManager().onSaveInstanceState();
            populateRecyclerView();
            // restore state
            readings_noise_student_RecyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
            refreshHandler.postDelayed(this, 5000);
        }
    }

    protected void populateRecyclerView(){
        List<NoiseSensorReading> lstSensorReadings = getSensorReadings();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        noiseSensorReadingRecyclerViewAdapter = new NoiseSensorReadingRecyclerViewAdapter(lstSensorReadings);
        readings_noise_student_RecyclerView = findViewById(R.id.readingsNoiseStudentRecyclerView);
        readings_noise_student_RecyclerView.setLayoutManager(llm);
        readings_noise_student_RecyclerView.setAdapter(noiseSensorReadingRecyclerViewAdapter);

        // need to remove the decoration. Else, the recyclerview keeps growing until it does not fit on the page
        if(readings_noise_student_RecyclerView.getItemDecorationCount() != 0){
            readings_noise_student_RecyclerView.removeItemDecorationAt(0);
        }
        readings_noise_student_RecyclerView.addItemDecoration(new DividerItemDecoration(readings_noise_student_RecyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }

    private List<NoiseSensorReading> getSensorReadings(){
        // TODO: get real sensor readings here
        // for now, populate a list of dummy sensor readings
        List<NoiseSensorReading> lstSensorReadings = new ArrayList<NoiseSensorReading>();
        lstSensorReadings.add(new NoiseSensorReading("Toronto Reading Room", TorontoN));
        lstSensorReadings.add(new NoiseSensorReading("Edmonton Reading Room", EdmontonN));
        lstSensorReadings.add(new NoiseSensorReading("Ottawa Reading Room", OttawaN));
        lstSensorReadings.add(new NoiseSensorReading("Calgary Reading Room", CalgaryN));
        lstSensorReadings.add(new NoiseSensorReading("Vancouver Reading Room", 69d));
        lstSensorReadings.add(new NoiseSensorReading("Montreal Reading Room", 20d));
        lstSensorReadings.add(new NoiseSensorReading("Moncton Reading Room", 140d));
        lstSensorReadings.add(new NoiseSensorReading("Regina Reading Room", 100d));
        return lstSensorReadings;
    }
}