package com.teamI.librarymonitoring.librarian;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.student.PassDataInterface;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class LibrarianSensorsConnectedActivity extends AppCompatActivity implements PassDataInterface {

    ListView sensors_connected_listview;
    ArrayList<String> Sensors;
    ArrayAdapter<String> Adapter;
    Button  add_sensor, delete_sensor;
    int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_librarian_sensors_connected);

        sensors_connected_listview = findViewById(R.id.SensorsConnected_listview);
        Sensors = new ArrayList<>();

        // Buttons
        add_sensor = findViewById(R.id.btnAddSensor);
        delete_sensor = findViewById(R.id.btnDeleteSensor);

        Sensors = getArray();

        Adapter = new ArrayAdapter<>(LibrarianSensorsConnectedActivity.this,android.R.layout.simple_list_item_1,Sensors);

        delete_sensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = 1;
                DeleteSensorsFragment deleteSensorsFragment = new DeleteSensorsFragment(LibrarianSensorsConnectedActivity.this);
                deleteSensorsFragment.show(getSupportFragmentManager(), "Delete Sensor");
            }
        });

        add_sensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = 2;
                AddSensorsFragment addSensorsFragment = new AddSensorsFragment(LibrarianSensorsConnectedActivity.this);
                addSensorsFragment.show(getSupportFragmentManager(), "Add Sensor");
            }
        });
        sensors_connected_listview.setAdapter(Adapter);


    }

    public ArrayList<String> getArray() {
        SharedPreferences sp = this.getSharedPreferences("Sensors Connected", Activity.MODE_PRIVATE);

        //NOTE: if shared preference is null, the method return empty Hashset and not null
        Set<String> set = sp.getStringSet("list", new HashSet<String>());

        return new ArrayList<String>(set);
    }

    public boolean saveArray() {
        SharedPreferences sp = this.getSharedPreferences("Sensors Connected", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Set<String> set = new HashSet<String>();
        set.addAll(Sensors);
        editor.putStringSet("list", set);
        return editor.commit();
    }

    @Override
    public void DataReceived(String data) {
        if(count == 2) {
            Sensors.add(data);
        }
        else if (count == 1)
        {
            for (int i=0;i<Sensors.size();i++)
            {
                if(Sensors.get(i).equals(data))
                {
                    Sensors.remove(i);
                    Adapter.notifyDataSetChanged();
                    break;
                }
            }
        }
        else if (count == 0)
        {
            Toast.makeText(LibrarianSensorsConnectedActivity.this,
                    "Returned to Sensors Connected Screen",Toast.LENGTH_LONG).show();

        }
    }

    @Override
    protected void onStop() {
        saveArray();
        super.onStop();
    }
}