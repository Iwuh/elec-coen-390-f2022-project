package com.teamI.librarymonitoring.student;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.teamI.librarymonitoring.IOpenDataResponseListener;
import com.teamI.librarymonitoring.OpenDataApiHelper;
import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.datacontainers.OccupancyData;
import com.teamI.librarymonitoring.datacontainers.ServiceHours;

import java.util.ArrayList;
import java.util.List;

public class Total_CapacityActivity extends AppCompatActivity {

    TextView librarycapacity_textview;
    private List<OccupancyData> occupancyData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_capacity);

        librarycapacity_textview = findViewById(R.id.librarycapacity_textview);

        occupancyData = new ArrayList<OccupancyData>();
        OpenDataApiHelper openDataApiHelper = new OpenDataApiHelper(this);

        openDataApiHelper.getOccupancy(occupancyData, new IOpenDataResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(Total_CapacityActivity.this, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse() {
                String totaloccupacy = occupancyData.get(0).getLibraryName() + ": " + occupancyData.get(0).getOccupancy() + " people\n" + occupancyData.get(0).getLastRecordTime()
                        + "\n\n" + occupancyData.get(1).getLibraryName() + ": " + occupancyData.get(1).getOccupancy() + " people\n" + occupancyData.get(1).getLastRecordTime()
                        + "\n\n" + occupancyData.get(2).getLibraryName() + ": " + occupancyData.get(2).getOccupancy() + " people\n" + occupancyData.get(2).getLastRecordTime();
                System.out.println(totaloccupacy);
                librarycapacity_textview.setText(totaloccupacy);
            }
        });


    }
}