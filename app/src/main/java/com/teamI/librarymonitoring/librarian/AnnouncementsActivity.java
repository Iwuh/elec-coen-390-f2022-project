package com.teamI.librarymonitoring.librarian;

import static com.teamI.helper.FirebaseHelper.CalgaryO;
import static com.teamI.helper.FirebaseHelper.EdmontonO;
import static com.teamI.helper.FirebaseHelper.OttawaO;
import static com.teamI.helper.FirebaseHelper.TorontoO;
import static com.teamI.helper.FirebaseHelper.VancouverO;

//import static com.teamI.helper.FirebaseHelper.announcements;
//import static com.teamI.helper.FirebaseHelper.a;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.JsonReader;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.teamI.helper.FirebaseHelper;
import com.teamI.librarymonitoring.AnnouncementRecyclerViewAdapter;
import com.teamI.librarymonitoring.PassAnnouncementInterface;
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

public class AnnouncementsActivity extends AppCompatActivity implements PassAnnouncementInterface {

    protected RecyclerView announcement_recyclerview;
    protected AnnouncementRecyclerViewAdapter announcementRecyclerViewAdapter;
    FirebaseHelper firebaseHelper = new FirebaseHelper(AnnouncementsActivity.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);

        announcement_recyclerview = findViewById(R.id.announcement_recyclerview);
        firebaseHelper.getAnnouncements();

    }


    public void populateRecyclerView(List<Announcement> announcements){
        List<Announcement> announcementList = announcements;
        LinearLayoutManager llm = new LinearLayoutManager(this);
        announcementRecyclerViewAdapter = new AnnouncementRecyclerViewAdapter(announcementList);
        announcement_recyclerview = findViewById(R.id.announcement_recyclerview);
        announcement_recyclerview.setLayoutManager(llm);
        announcement_recyclerview.setAdapter(announcementRecyclerViewAdapter);

        announcement_recyclerview.addItemDecoration(new DividerItemDecoration(announcement_recyclerview.getContext(), DividerItemDecoration.VERTICAL));
    }


    @Override
    public void AnnouncementReceived(List<Announcement> announcement) {
        populateRecyclerView(announcement);
    }
}