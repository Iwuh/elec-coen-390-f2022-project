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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);

        announcement_recyclerview = findViewById(R.id.announcement_recyclerview);

        populateRecyclerView();

    }

    protected void populateRecyclerView(){
        List<Announcement> announcementList = getAnnouncementData();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        announcementRecyclerViewAdapter = new AnnouncementRecyclerViewAdapter(announcementList);
        announcement_recyclerview = findViewById(R.id.announcement_recyclerview);
        announcement_recyclerview.setLayoutManager(llm);
        announcement_recyclerview.setAdapter(announcementRecyclerViewAdapter);

        announcement_recyclerview.addItemDecoration(new DividerItemDecoration(announcement_recyclerview.getContext(), DividerItemDecoration.VERTICAL));
    }

    private List<Announcement> getAnnouncementData(){
        // for now, populate a list of dummy sensor readings
        List<Announcement> announcementList = new ArrayList<Announcement>();
        //SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        //String timestamp = "Timestamp: " + s.format(new Date());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String announcement = extras.getString("announcement");
            String Timestamp = extras.getString("announcement_timestamp");

                System.out.println(announcement + ": " + Timestamp);
                announcementList.add(new Announcement(announcement, Timestamp));
        }
        return announcementList;
    }

    private static Context context;
    private static SharedPreferences sharedPreferences = context.getSharedPreferences("Announcements", Context.MODE_PRIVATE);
    private static SharedPreferences.Editor editor = sharedPreferences.edit();


    public <T> void setList(String key, List<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        set(key, json);
    }

    public static void set(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public List<Announcement> getList(){
        List<Announcement> arrayItems = null;
        String serializedObject = sharedPreferences.getString("Announcements", null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Announcement>>(){}.getType();
            arrayItems = gson.fromJson(serializedObject, type);
        }
        return arrayItems;
    }

}