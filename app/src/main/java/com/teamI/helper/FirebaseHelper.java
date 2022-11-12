package com.teamI.helper;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FirebaseHelper {

    String getData;
    public static String Ottawa;
    public static String Toronto;
    public static String Edmonton;
    DatabaseReference SensorsData;

    public void Noise_sensor(Context context, String name) {
        SensorsData = FirebaseDatabase.getInstance().getReference();
        SensorsData = SensorsData.child(name);
        Query query = SensorsData.child("Noise_sensor");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("elarg", "onDataChange: " + snapshot);
                Ottawa = snapshot.child("strMeasurement").getValue(String.class);

//                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });
    }

    public void Occ_sensor1(Context context, String name) {
        SensorsData = FirebaseDatabase.getInstance().getReference();
        SensorsData = SensorsData.child(name);
        Query query1 = SensorsData.child("Occ_sensor1");

        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("elarg", "onDataChange: " + snapshot);
                Toronto = snapshot.child("strMeasurement").getValue(String.class);

//                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });
    }

    public void Occ_sensor2(Context context, String name) {
        SensorsData = FirebaseDatabase.getInstance().getReference();
        SensorsData = SensorsData.child(name);
        Query query2 = SensorsData.child("Occ_sensor2");
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("elarg", "onDataChange: " + snapshot);
                Edmonton = snapshot.child("strMeasurement").getValue(String.class);

//                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });
    }
}
