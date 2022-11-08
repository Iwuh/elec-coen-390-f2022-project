package com.teamI.librarymonitoring;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.teamI.librarymonitoring.datacontainers.ServiceHours;

import java.util.ArrayList;
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
            }
        });
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


}