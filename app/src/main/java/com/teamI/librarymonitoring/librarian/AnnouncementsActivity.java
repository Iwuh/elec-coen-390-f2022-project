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

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.JsonReader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.teamI.helper.FirebaseHelper;
import com.teamI.librarymonitoring.AnnouncementRecyclerViewAdapter;
import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.SensorReadingRecyclerViewAdapter;
import com.teamI.librarymonitoring.datacontainers.Announcement;
import com.teamI.librarymonitoring.datacontainers.SensorReading;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnnouncementsActivity extends AppCompatActivity {

    protected RecyclerView announcement_recyclerview;
    protected AnnouncementRecyclerViewAdapter announcementRecyclerViewAdapter;
    FirebaseHelper firebaseHelper;
    List<Announcement> announcements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);

        announcement_recyclerview = findViewById(R.id.announcement_recyclerview);

        //announcements = firebaseHelper.getAnnouncements();



       // populateRecyclerView();

    }

    /*protected void populateRecyclerView(){
        List<Announcement> announcementList = getAnnouncementData();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        announcementRecyclerViewAdapter = new AnnouncementRecyclerViewAdapter(announcementList);
        announcement_recyclerview = findViewById(R.id.announcement_recyclerview);
        announcement_recyclerview.setLayoutManager(llm);
        announcement_recyclerview.setAdapter(announcementRecyclerViewAdapter);

        announcement_recyclerview.addItemDecoration(new DividerItemDecoration(announcement_recyclerview.getContext(), DividerItemDecoration.VERTICAL));
    }

    private List<Announcement> getAnnouncementData(){
        List<Announcement> announcementList = new ArrayList<Announcement>();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String announcement = extras.getString("announcement");
            String Timestamp = extras.getString("announcement_timestamp");

                System.out.println(announcement + ": " + Timestamp);
                announcementList.add(new Announcement(announcement, Timestamp));
        }
        return announcementList;
    }*/


}