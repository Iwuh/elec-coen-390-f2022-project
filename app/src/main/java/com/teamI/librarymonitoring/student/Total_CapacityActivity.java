package com.teamI.librarymonitoring.student;

import static com.teamI.librarymonitoring.SharedPreferenceUtility.getHoursTimeStamp;
import static com.teamI.librarymonitoring.SharedPreferenceUtility.getOccupancyTimeStamp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.teamI.librarymonitoring.IOpenDataResponseListener;
import com.teamI.librarymonitoring.OpenDataApiHelper;
import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.SharedPreferenceUtility;
import com.teamI.librarymonitoring.datacontainers.OccupancyData;
import com.teamI.librarymonitoring.datacontainers.ServiceHours;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Total_CapacityActivity extends AppCompatActivity {

    TextView librarycapacity_textview;
    private List<OccupancyData> occupancyData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_capacity);

        librarycapacity_textview = (TextView) findViewById(R.id.librarycapacity_textview);

        occupancyData = new ArrayList<OccupancyData>();

        callOpenApi();

        Date savedLastUpdatedCapacity = null;
        try {
            savedLastUpdatedCapacity = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy").parse(getOccupancyTimeStamp(this));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        /**
         * The handler will create a thread that runs every 90000(15 minutes)
         * the thread we check the time difference since the last update  or api call
         * and if the timestamp difference is more than or equal to 15 mins we call the api
         * **/
        final Handler ha = new Handler();
        Date finalSavedLastUpdatedCapacity = savedLastUpdatedCapacity;
        ha.postDelayed(new Runnable() {
            @Override
            public void run() {
                Date currentTimeStamp = new Date();
                long diff = currentTimeStamp.getTime() - finalSavedLastUpdatedCapacity.getTime();
                long minutes = (diff / 1000) / 60;
                Toast.makeText(Total_CapacityActivity.this, minutes + "Minute Time difference", Toast.LENGTH_LONG).show();

                if (minutes >= 15) {
                    callOpenApi();
                }

                ha.postDelayed(this, 900000);
            }
        }, 900000);

    }

            private void callOpenApi(){
            OpenDataApiHelper openDataApiHelper = new OpenDataApiHelper(this);
            openDataApiHelper.getOccupancy(occupancyData, new IOpenDataResponseListener() {
                @Override
                public void onError(String message) {
                    Toast.makeText(Total_CapacityActivity.this, message, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onResponse() {
                    String totaloccupancy = occupancyData.get(0).getLibraryName() + ": " + occupancyData.get(0).getOccupancy() + " people\n" + occupancyData.get(0).getLastRecordTime()
                            + "\n\n" + occupancyData.get(1).getLibraryName() + ": " + occupancyData.get(1).getOccupancy() + " people\n" + occupancyData.get(1).getLastRecordTime()
                            + "\n\n" + occupancyData.get(2).getLibraryName() + ": " + occupancyData.get(2).getOccupancy() + " people\n" + occupancyData.get(2).getLastRecordTime();
                    System.out.println(totaloccupancy);
                    librarycapacity_textview.setText(totaloccupancy);
                    Date date = new Date();
                    SharedPreferenceUtility.setOccupancyTimeStamp(librarycapacity_textview.getContext(), date);
                }
            });


        }
    }