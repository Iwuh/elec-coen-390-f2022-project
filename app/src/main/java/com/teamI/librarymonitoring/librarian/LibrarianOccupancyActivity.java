package com.teamI.librarymonitoring.librarian;

import static com.teamI.helper.FirebaseHelper.CalgaryO;
import static com.teamI.helper.FirebaseHelper.EdmontonO;
import static com.teamI.helper.FirebaseHelper.OttawaO;
import static com.teamI.helper.FirebaseHelper.TorontoO;
import static com.teamI.helper.FirebaseHelper.VancouverO;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.teamI.helper.FirebaseHelper;
import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.OccupancySensorReadingRecyclerViewAdapter;
import com.teamI.librarymonitoring.datacontainers.OccupancySensorReading;

import java.util.ArrayList;
import java.util.List;

public class LibrarianOccupancyActivity extends AppCompatActivity {

    protected RecyclerView readingsRecyclerView;
    protected OccupancySensorReadingRecyclerViewAdapter occupancySensorReadingRecyclerViewAdapter;
    protected FloatingActionButton fBtnOccupancyDetails;
    protected Handler refreshHandler;
    protected final static int msBetweenUpdates = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_librarian_occupancy);

        populateRecyclerView();

                fBtnOccupancyDetails = findViewById(R.id.fBtnOccupancyDetails);
        fBtnOccupancyDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayOccupancyDetails();
            }
        }
        );

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
            firebaseHelper.refreshAllOccupancyReadings(LibrarianOccupancyActivity.this);
            Parcelable recyclerViewState = readingsRecyclerView.getLayoutManager().onSaveInstanceState();
            populateRecyclerView();
            // restore state
            // this is to avoid the recyclerview scrolling to top on every refresh
            readingsRecyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
            refreshHandler.postDelayed(this, 5000);
        }
    }

    protected void populateRecyclerView(){
        List<OccupancySensorReading> lstSensorReadings = getSensorReadings();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        occupancySensorReadingRecyclerViewAdapter = new OccupancySensorReadingRecyclerViewAdapter(lstSensorReadings);
        readingsRecyclerView = findViewById(R.id.readingsOccLibrarianRecyclerView);
        readingsRecyclerView.setLayoutManager(llm);
        readingsRecyclerView.setAdapter(occupancySensorReadingRecyclerViewAdapter);

        // need to remove the decoration. Else, the recyclerview keeps growing until it does not fit on the page
        if(readingsRecyclerView.getItemDecorationCount() != 0){
            readingsRecyclerView.removeItemDecorationAt(0);
        }
        readingsRecyclerView.addItemDecoration(new DividerItemDecoration(readingsRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }


    private List<OccupancySensorReading> getSensorReadings(){
        // TODO: get real sensor readings here
        // for now, populate a list of dummy sensor readings
        List<OccupancySensorReading> lstOccupancySensorReadings = new ArrayList<OccupancySensorReading>();
        lstOccupancySensorReadings.add(new OccupancySensorReading("Toronto Reading Room", ""+TorontoO));
        lstOccupancySensorReadings.add(new OccupancySensorReading("Edmonton Reading Room", ""+EdmontonO));
        lstOccupancySensorReadings.add(new OccupancySensorReading("Ottawa Reading Room", ""+OttawaO));
        lstOccupancySensorReadings.add(new OccupancySensorReading("Calgary Reading Room", ""+CalgaryO));
        lstOccupancySensorReadings.add(new OccupancySensorReading("Vancouver Reading Room", ""+VancouverO));
        lstOccupancySensorReadings.add(new OccupancySensorReading("Montreal Reading Room", "50"));
        lstOccupancySensorReadings.add(new OccupancySensorReading("Moncton Reading Room", "514"));
        lstOccupancySensorReadings.add(new OccupancySensorReading("Regina Reading Room", "611"));
        return lstOccupancySensorReadings;
    }

    private void displayOccupancyDetails(){
        // TODO: display occupancy details exclusive to librarian
        Toast.makeText(this, "Not implemented.", Toast.LENGTH_SHORT).show();
    }
}