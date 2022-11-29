package com.teamI.helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.teamI.librarymonitoring.datacontainers.Announcement;

import java.util.ArrayList;
import java.util.List;


// TODO: this class should definitely be cleaned up, it is not extensible at all

public class FirebaseHelper {

    String getData;
    public static Double OttawaN;
    public static Double TorontoN;
    public static Double EdmontonN;
    public static Double CalgaryN;
    public static Double VancouverN;
    public static String OttawaO;
    public static String TorontoO;
    public static String EdmontonO;
    public static String CalgaryO;
    public static String VancouverO;
    DatabaseReference SensorsData;

    // TODO: why do we need to pass a string to every function?
    // in all function calls, the string passed ends up being "Sensors" anyway

    public void Noise_sensor1(Context context, String name) {
        SensorsData = FirebaseDatabase.getInstance().getReference();
        SensorsData = SensorsData.child(name);
        Query query = SensorsData.child("Noise_sensor1");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("elarg", "onDataChange: " + snapshot);
                TorontoN = snapshot.child("floatAvg").getValue(Double.class);

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
                EdmontonN = snapshot.child("floatAvg").getValue(Double.class);

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
                OttawaN = snapshot.child("floatAvg").getValue(Double.class);

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
                CalgaryN = snapshot.child("floatAvg").getValue(Double.class);

//                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });
    }

    public void refreshAllNoiseLevelReadings(Context context){
        this.Noise_sensor1(context, "Sensors");
        this.Noise_sensor2(context, "Sensors");
        this.Noise_sensor3(context, "Sensors");
        this.Noise_sensor4(context, "Sensors");
    }

    // function to refresh all occupancy sensors
    public void refreshAllOccupancyReadings(Context context){
        this.Occ_sensor1(context, "Sensors");
        this.Occ_sensor2(context, "Sensors");
        this.Occ_sensor3(context, "Sensors");
        this.Occ_sensor4(context, "Sensors");
        this.Occ_sensor5(context, "Sensors");

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

    public List<Announcement> getAnnouncements(){
        List<Announcement> announcements = new ArrayList<Announcement>();
        DatabaseReference databaseRef =  FirebaseDatabase.getInstance().getReference().child("Announcements");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("elarg", "onDataChange: " + snapshot);

                for ( DataSnapshot snap : snapshot.getChildren() ){
                    Announcement announcement= snap.getValue(Announcement.class);
                    announcements.add(announcement);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Firebase", "Failed to read data");
            }
        });

        return announcements;
    }

    /***
     * This method is used to create announcements.
     * you can only pass one announcement at a timre
     * use example: firebaseHelper.setAnnouncement(new Announcement("message",new Date().toString()));
     * @param announcement
     */
    public void setAnnouncement(Announcement announcement) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("Announcements");
        String key = databaseRef.push().getKey();
        databaseRef.child(key).setValue(announcement).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.i("setAnnouncement", "Announcement Successufly added to the db "+ announcement);
            }

        } ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("setAnnouncement", "Failed to add Announcement to the db "+ announcement);
            }
        });
    }

    /**
     * This will check if the number given is equal or less than the total announcement
     * we have in the db, before returning them.
     * @param number
     * @return allAnnouncemnt
     */
    public List<Announcement> getAnnouncementNumber(int number){
        List<Announcement> allAnnouncement = getAnnouncements();
        if(allAnnouncement.size() >= number && number > 0){
            return allAnnouncement.subList(0,number);
        }else{
            Log.w("getAnnouncementNumber","Invalid Number of Announcement given");
            return  allAnnouncement;
        }
    }
}
