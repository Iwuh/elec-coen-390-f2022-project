package com.teamI.librarymonitoring.student;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.teamI.librarymonitoring.R;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity implements PassDataInterface {

    ListView favorites_listview;
    FloatingActionButton FAB_favorites;
    private ArrayList<String> favorite_rooms;
    ArrayAdapter<String> Adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        favorites_listview = findViewById(R.id.favorites_listview);
        FAB_favorites = findViewById(R.id.fABtn_favorites);
        favorite_rooms = new ArrayList<>();
        Adapter = new ArrayAdapter<>(FavoritesActivity.this, android.R.layout.simple_list_item_1,favorite_rooms);

        FAB_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavoritesFragment Favorites_Fragment = new FavoritesFragment(FavoritesActivity.this);
                Favorites_Fragment.show(getSupportFragmentManager(), "Add Entry");
            }
        });

        favorites_listview.setAdapter(Adapter);

    }

    @Override
    public void DataReceived(String data) {
        favorite_rooms.add(data);
    }
}