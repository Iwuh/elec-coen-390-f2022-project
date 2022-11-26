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
import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.SensorReadingRecyclerViewAdapter;
import com.teamI.librarymonitoring.datacontainers.SensorReading;

import java.util.ArrayList;
import java.util.List;

public class StudentNoiseLevelActivity extends AppCompatActivity {

    protected RecyclerView readings_noise_student_RecyclerView;
    protected SensorReadingRecyclerViewAdapter sensorReadingRecyclerViewAdapter;
    protected Handler refreshHandler;
    protected static final int msBetweenUpdates = 20000;


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
        List<SensorReading> lstSensorReadings = getSensorReadings();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        sensorReadingRecyclerViewAdapter = new SensorReadingRecyclerViewAdapter(lstSensorReadings);
        readings_noise_student_RecyclerView = findViewById(R.id.readingsNoiseStudentRecyclerView);
        readings_noise_student_RecyclerView.setLayoutManager(llm);
        readings_noise_student_RecyclerView.setAdapter(sensorReadingRecyclerViewAdapter);

        // need to remove the decoration. Else, the recyclerview keeps growing until it does not fit on the page
        if(readings_noise_student_RecyclerView.getItemDecorationCount() != 0){
            readings_noise_student_RecyclerView.removeItemDecorationAt(0);
        }
        readings_noise_student_RecyclerView.addItemDecoration(new DividerItemDecoration(readings_noise_student_RecyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }

    private List<SensorReading> getSensorReadings(){
        // TODO: get real sensor readings here
        // for now, populate a list of dummy sensor readings
        List<SensorReading> lstSensorReadings = new ArrayList<SensorReading>();
        lstSensorReadings.add(new SensorReading("Toronto Reading Room", ""+TorontoN, "dB"));
        lstSensorReadings.add(new SensorReading("Edmonton Reading Room", ""+EdmontonN, "dB"));
        lstSensorReadings.add(new SensorReading("Ottawa Reading Room", ""+OttawaN, "dB"));
        lstSensorReadings.add(new SensorReading("Calgary Reading Room", ""+CalgaryN, "dB"));
        lstSensorReadings.add(new SensorReading("Vancouver Reading Room", "69", "dB"));
        lstSensorReadings.add(new SensorReading("Montreal Reading Room", "62", "dB"));
        lstSensorReadings.add(new SensorReading("Moncton Reading Room", "90", "dB"));
        lstSensorReadings.add(new SensorReading("Regina Reading Room", "5", "dB"));
        return lstSensorReadings;
    }
}