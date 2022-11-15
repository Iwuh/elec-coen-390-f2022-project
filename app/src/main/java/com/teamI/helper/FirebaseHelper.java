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
    public static String OttawaN;
    public static String TorontoN;
    public static String EdmontonN;
    public static String CalgaryN;
    public static String VancouverN;
    public static String OttawaO;
    public static String TorontoO;
    public static String EdmontonO;
    public static String CalgaryO;
    public static String VancouverO;
    DatabaseReference SensorsData;

    public void Noise_sensor1(Context context, String name) {
        SensorsData = FirebaseDatabase.getInstance().getReference();
        SensorsData = SensorsData.child(name);
        Query query = SensorsData.child("Noise_sensor1");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("elarg", "onDataChange: " + snapshot);
                TorontoN = snapshot.child("strMeasurement").getValue(String.class);

//                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });
    }

    public void Noise_sensor2(Context context, String name) {
        SensorsData = FirebaseDatabase.getInstance().getReference();
        SensorsData = SensorsData.child(name);
        Query query = SensorsData.child("Noise_sensor2");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("elarg", "onDataChange: " + snapshot);
                EdmontonN = snapshot.child("strMeasurement").getValue(String.class);

//                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });
    }

    public void Noise_sensor3(Context context, String name) {
        SensorsData = FirebaseDatabase.getInstance().getReference();
        SensorsData = SensorsData.child(name);
        Query query = SensorsData.child("Noise_sensor3");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("elarg", "onDataChange: " + snapshot);
                OttawaN = snapshot.child("strMeasurement").getValue(String.class);

//                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });
    }

    public void Noise_sensor4(Context context, String name) {
        SensorsData = FirebaseDatabase.getInstance().getReference();
        SensorsData = SensorsData.child(name);
        Query query = SensorsData.child("Noise_sensor4");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("elarg", "onDataChange: " + snapshot);
                CalgaryN = snapshot.child("strMeasurement").getValue(String.class);

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
                TorontoO = snapshot.child("strMeasurement").getValue(String.class);

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
                EdmontonO = snapshot.child("strMeasurement").getValue(String.class);

//                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });
    }

    public void Occ_sensor3(Context context, String name) {
        SensorsData = FirebaseDatabase.getInstance().getReference();
        SensorsData = SensorsData.child(name);
        Query query2 = SensorsData.child("Occ_sensor3");
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("elarg", "onDataChange: " + snapshot);
                OttawaO = snapshot.child("strMeasurement").getValue(String.class);

//                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });
    }

    public void Occ_sensor4(Context context, String name) {
        SensorsData = FirebaseDatabase.getInstance().getReference();
        SensorsData = SensorsData.child(name);
        Query query2 = SensorsData.child("Occ_sensor4");
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("elarg", "onDataChange: " + snapshot);
                CalgaryO = snapshot.child("strMeasurement").getValue(String.class);

//                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });
    }

    public void Occ_sensor5(Context context, String name) {
        SensorsData = FirebaseDatabase.getInstance().getReference();
        SensorsData = SensorsData.child(name);
        Query query2 = SensorsData.child("Occ_sensor5");
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("elarg", "onDataChange: " + snapshot);
                VancouverO = snapshot.child("strMeasurement").getValue(String.class);

//                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });
    }
}
