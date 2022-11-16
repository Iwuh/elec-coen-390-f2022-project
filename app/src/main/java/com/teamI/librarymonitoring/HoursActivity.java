package com.teamI.librarymonitoring;

import static com.teamI.librarymonitoring.SharedPreferenceUtility.clearAll;
import static com.teamI.librarymonitoring.SharedPreferenceUtility.getHoursTimeStamp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import com.teamI.librarymonitoring.datacontainers.LibraryComputerData;
import com.teamI.librarymonitoring.datacontainers.OccupancyData;
import com.teamI.librarymonitoring.datacontainers.ServiceHours;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HoursActivity extends AppCompatActivity {
    private List<ServiceHours> allServiceHours;
    private RecyclerView hoursRecyclerView;
    private HoursRecyclerViewAdapter hoursRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hours);

        // needed because the screen can be started from either librarian or student versions
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        allServiceHours = new ArrayList<ServiceHours>();
        hoursRecyclerView = findViewById(R.id.recyclerViewHours);

        //This will be the first api call, after this call all other calls will have to be
        // checked if the time elapsed is more than 15 min
        callOpenApi();

        Date savedLastUpdatedHours = null;
        try {
            savedLastUpdatedHours = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy").parse(getHoursTimeStamp(this));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        /**
         * The handler will create a thread that runs every 90000(15 minutes)
         * the thread we check the time difference since the last update  or api call
         * and if the timestamp difference is more than or equal to 15 mins we call the api
         * **/
        final Handler ha=new Handler();
        Date finalSavedLastUpdatedHours = savedLastUpdatedHours;
        ha.postDelayed(new Runnable() {
            @Override
            public void run() {
                    Date currentTimeStamp = new Date();
                    long diff = currentTimeStamp.getTime() - finalSavedLastUpdatedHours.getTime();
                    long minutes = (diff/1000)/60;
                 // Toast.makeText(HoursActivity.this, minutes + "Minute Time difference", Toast.LENGTH_LONG).show();

                    if(minutes >= 15){
                        callOpenApi();
                    }

                ha.postDelayed(this, 900000);
            }
        }, 900000);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return false;
    }

    private void populateRecyclerView() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        hoursRecyclerViewAdapter = new HoursRecyclerViewAdapter(allServiceHours);
        hoursRecyclerView.setLayoutManager(llm);
        hoursRecyclerView.setAdapter(hoursRecyclerViewAdapter);

        hoursRecyclerView.addItemDecoration(new DividerItemDecoration(hoursRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

    }

    private void callOpenApi(){
        OpenDataApiHelper openDataApiHelper = new OpenDataApiHelper(this);
        openDataApiHelper.getHours(allServiceHours, new IOpenDataResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(HoursActivity.this, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse() {
                // once OpenData is fetched, use the list to populate RecyclerView
                populateRecyclerView();

                //Record the timestamp for the hours activity response
                Date date = new Date();
                SharedPreferenceUtility.setHoursTimeStamp(hoursRecyclerView.getContext(),date);

            }
        });
    }


}