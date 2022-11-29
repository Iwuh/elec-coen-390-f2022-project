package com.teamI.librarymonitoring.librarian;

import static com.teamI.helper.FirebaseHelper.CalgaryN;
import static com.teamI.helper.FirebaseHelper.EdmontonN;
import static com.teamI.helper.FirebaseHelper.OttawaN;
import static com.teamI.helper.FirebaseHelper.TorontoN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.teamI.helper.FirebaseHelper;
import com.teamI.librarymonitoring.NoiseSensorReadingRecyclerViewAdapter;
import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.OccupancySensorReadingRecyclerViewAdapter;
import com.teamI.librarymonitoring.datacontainers.NoiseSensorReading;
import com.teamI.librarymonitoring.datacontainers.OccupancySensorReading;

import java.util.ArrayList;
import java.util.List;


public class LibrarianNoiseLevelActivity extends AppCompatActivity {

    protected RecyclerView readings_noise_librarian_RecyclerView;
    protected NoiseSensorReadingRecyclerViewAdapter noiseSensorReadingRecyclerViewAdapter;
    protected FloatingActionButton FAB_noisedetails;
    protected Handler refreshHandler;
    protected static final int msBetweenUpdates = 15000;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_librarian_noise_level);


        FAB_noisedetails = findViewById(R.id.faBtnNoiseLevelDetail);

        FAB_noisedetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayNoiseDetails();
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
            firebaseHelper.refreshAllNoiseLevelReadings(LibrarianNoiseLevelActivity.this);
            Parcelable recyclerViewState = readings_noise_librarian_RecyclerView.getLayoutManager().onSaveInstanceState();
            populateRecyclerView();
            // restore state
            readings_noise_librarian_RecyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
            refreshHandler.postDelayed(this, 5000);
        }
    }

    protected void populateRecyclerView(){
        List<NoiseSensorReading> lstSensorReadings = getSensorReadings();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        noiseSensorReadingRecyclerViewAdapter = new NoiseSensorReadingRecyclerViewAdapter(lstSensorReadings);
        readings_noise_librarian_RecyclerView = findViewById(R.id.readingsNoiseLibrarianRecyclerView);
        readings_noise_librarian_RecyclerView.setLayoutManager(llm);
        readings_noise_librarian_RecyclerView.setAdapter(noiseSensorReadingRecyclerViewAdapter);

        // need to remove the decoration. Else, the recyclerview keeps growing until it does not fit on the page
        if(readings_noise_librarian_RecyclerView.getItemDecorationCount() != 0){
            readings_noise_librarian_RecyclerView.removeItemDecorationAt(0);
        }

        readings_noise_librarian_RecyclerView.addItemDecoration(new DividerItemDecoration(readings_noise_librarian_RecyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }

    private List<NoiseSensorReading> getSensorReadings(){
        // TODO: get real sensor readings here
        // TODO: the unit is not dB anymore! Map the floatAvg value to the noise level
        // for now, populate a list of dummy sensor readings
        List<NoiseSensorReading> lstSensorReadings = new ArrayList<NoiseSensorReading>();
        lstSensorReadings.add(new NoiseSensorReading("Toronto Reading Room", Double.parseDouble(TorontoN)));
        lstSensorReadings.add(new NoiseSensorReading("Edmonton Reading Room",  Double.parseDouble(EdmontonN)));
        lstSensorReadings.add(new NoiseSensorReading("Ottawa Reading Room",  Double.parseDouble(OttawaN)));
        lstSensorReadings.add(new NoiseSensorReading("Calgary Reading Room",  Double.parseDouble(CalgaryN)));
        lstSensorReadings.add(new NoiseSensorReading("Vancouver Reading Room", 69d));
        lstSensorReadings.add(new NoiseSensorReading("Montreal Reading Room", 20d));
        lstSensorReadings.add(new NoiseSensorReading("Moncton Reading Room", 140d));
        lstSensorReadings.add(new NoiseSensorReading("Regina Reading Room", 100d));
        return lstSensorReadings;
    }

    private void displayNoiseDetails(){
        // TODO: display noise details exclusive to librarian
        Toast.makeText(this, "Not implemented.", Toast.LENGTH_SHORT).show();
    }
}