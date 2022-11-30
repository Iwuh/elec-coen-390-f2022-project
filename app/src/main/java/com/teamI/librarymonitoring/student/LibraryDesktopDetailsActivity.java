package com.teamI.librarymonitoring.student;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.teamI.librarymonitoring.DesktopsRecyclerViewAdapter;
import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.datacontainers.DesktopRoom;
import com.teamI.librarymonitoring.datacontainers.LibraryComputerData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class LibraryDesktopDetailsActivity extends AppCompatActivity {

    protected RecyclerView recyclerViewDesktopsByRoom;
    protected LibraryComputerData lcDataToDisplay;
    protected List<DesktopRoom> lstDesktopRooms;
    protected DesktopsRecyclerViewAdapter desktopsRecyclerViewAdapter;
    protected TextView textViewLibraryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_desktop_details);

        lcDataToDisplay = (LibraryComputerData) getIntent().getSerializableExtra("Library");
        lstDesktopRooms = getListOfDesktopRooms(lcDataToDisplay);
        recyclerViewDesktopsByRoom = findViewById(R.id.recyclerViewDesktopsByRoom);
        textViewLibraryName = findViewById(R.id.textViewLibraryName);

        textViewLibraryName.setText(lcDataToDisplay.getLibraryName() + " Library");
        populateRecyclerView();
    }

    private void populateRecyclerView() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        desktopsRecyclerViewAdapter = new DesktopsRecyclerViewAdapter(lstDesktopRooms);
        recyclerViewDesktopsByRoom.setLayoutManager(llm);
        recyclerViewDesktopsByRoom.setAdapter(desktopsRecyclerViewAdapter);

        recyclerViewDesktopsByRoom.addItemDecoration(new DividerItemDecoration(recyclerViewDesktopsByRoom.getContext(), DividerItemDecoration.VERTICAL));
    }

    private List<DesktopRoom> getListOfDesktopRooms(LibraryComputerData lcDataToDisplay) {
        List<DesktopRoom> toReturn = new ArrayList<DesktopRoom>();
        for(Map.Entry<String, Integer> entry: lcDataToDisplay.getMapDesktopsInRooms().entrySet()){
            String strRoomCode = entry.getKey();
            Integer nNbrOfDesktops = entry.getValue();
            toReturn.add(new DesktopRoom(strRoomCode, nNbrOfDesktops));
        }

        return toReturn;


    }


}