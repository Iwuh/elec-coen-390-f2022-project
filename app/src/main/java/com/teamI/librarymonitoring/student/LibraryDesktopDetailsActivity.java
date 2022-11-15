package com.teamI.librarymonitoring.student;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.datacontainers.LibraryComputerData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class LibraryDesktopDetailsActivity extends AppCompatActivity {

    protected LibraryComputerData lcDataToDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_desktop_details);

        lcDataToDisplay = (LibraryComputerData) getIntent().getSerializableExtra("Library");
        TextView textViewLibraryName = findViewById(R.id.textViewLibraryName);
        textViewLibraryName.setText(lcDataToDisplay.getLibraryName() + " Library Available Desktops");
        populateListView();
    }

    private void populateListView() {
        List<String> allStringsToDisplay = new ArrayList<String>();
        for(Map.Entry<String, Integer> entry : lcDataToDisplay.getMapDesktopsInRooms().entrySet()){
            String entryString = entry.getKey() + ": " + Integer.toString(entry.getValue());
            allStringsToDisplay.add(entryString);
        }

        ListView listViewRoomDesktops = findViewById(R.id.listViewRoomsDesktops);
        ArrayAdapter<String> lstRoomsComputers = new ArrayAdapter<String>(this, R.layout.list_view_element, allStringsToDisplay);
        listViewRoomDesktops.setAdapter(lstRoomsComputers);
    }
}