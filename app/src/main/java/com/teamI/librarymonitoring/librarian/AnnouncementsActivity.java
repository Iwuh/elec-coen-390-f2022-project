package com.teamI.librarymonitoring.librarian;

import static com.teamI.helper.FirebaseHelper.CalgaryO;
import static com.teamI.helper.FirebaseHelper.EdmontonO;
import static com.teamI.helper.FirebaseHelper.OttawaO;
import static com.teamI.helper.FirebaseHelper.TorontoO;
import static com.teamI.helper.FirebaseHelper.VancouverO;

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
    FirebaseHelper firebaseHelper = new FirebaseHelper();
    List<Announcement> announcements = new ArrayList<>();

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Announcements");

        announcement_recyclerview = findViewById(R.id.announcement_recyclerview);

        getdata();

    }

    private void getdata() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for ( DataSnapshot snap : snapshot.getChildren() ) {
                    Announcement announcement = snap.getValue(Announcement.class);
                    announcements.add(announcement);
                }
                populateRecyclerView(announcements);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    protected void populateRecyclerView(List<Announcement> announcements_list){
        List<Announcement> announcementList = announcements_list;
        LinearLayoutManager llm = new LinearLayoutManager(this);
        announcementRecyclerViewAdapter = new AnnouncementRecyclerViewAdapter(announcementList);
        announcement_recyclerview = findViewById(R.id.announcement_recyclerview);
        announcement_recyclerview.setLayoutManager(llm);
        announcement_recyclerview.setAdapter(announcementRecyclerViewAdapter);

        announcement_recyclerview.addItemDecoration(new DividerItemDecoration(announcement_recyclerview.getContext(), DividerItemDecoration.VERTICAL));
    }


}