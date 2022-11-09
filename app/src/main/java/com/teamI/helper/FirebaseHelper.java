package com.teamI.helper;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseHelper {

    String getData;
    DatabaseReference SensorsData;
    public void readData(Context context, String name){
        SensorsData = FirebaseDatabase.getInstance().getReference();
        SensorsData = SensorsData.child(name);
        SensorsData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("elarg", "onDataChange: "+snapshot);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });
    }
}
