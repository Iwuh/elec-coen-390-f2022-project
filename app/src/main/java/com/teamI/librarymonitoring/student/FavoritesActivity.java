package com.teamI.librarymonitoring.student;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.teamI.librarymonitoring.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FavoritesActivity extends AppCompatActivity implements PassDataInterface {

    ListView favorites_listview;
    FloatingActionButton FAB_favorites;
    ArrayList<String> favorite_rooms;
    ArrayAdapter<String> Adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        favorites_listview = findViewById(R.id.favorites_listview);
        FAB_favorites = findViewById(R.id.fABtn_favorites);
        favorite_rooms = new ArrayList<>();


        favorite_rooms = getArray();

        Adapter = new ArrayAdapter<>(FavoritesActivity.this, android.R.layout.simple_list_item_1,favorite_rooms);

        favorite_rooms.add("Toronto    100 people    20dB");
        favorite_rooms.add("Ukraine    30 people    40dB");
        favorite_rooms.add("Nigeria    10 people    100dB");


        FAB_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavoritesFragment Favorites_Fragment = new FavoritesFragment(FavoritesActivity.this);
                Favorites_Fragment.show(getSupportFragmentManager(), "Add Room");
            }
        });
        favorites_listview.setAdapter(Adapter);

    }

    public ArrayList<String> getArray() {
        SharedPreferences sp = this.getSharedPreferences("Names_of_Favorite_Rooms", Activity.MODE_PRIVATE);

        //NOTE: if shared preference is null, the method return empty Hashset and not null
        Set<String> set = sp.getStringSet("list", new HashSet<String>());

        return new ArrayList<String>(set);
    }

    public boolean saveArray() {
        SharedPreferences sp = this.getSharedPreferences("Names_of_Favorite_Rooms", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Set<String> set = new HashSet<String>();
        set.addAll(favorite_rooms);
        editor.putStringSet("list", set);
        return editor.commit();
    }

    @Override
    public void DataReceived(String data) {
        favorite_rooms.add(data);
        Adapter.notifyDataSetChanged();

    }

    @Override
    protected void onStop() {
        //saveArray();
        super.onStop();
    }
}