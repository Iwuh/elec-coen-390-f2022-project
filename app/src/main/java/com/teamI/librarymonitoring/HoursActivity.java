package com.teamI.librarymonitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.net.MalformedURLException;

public class HoursActivity extends AppCompatActivity {

    protected String apiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hours);

        apiKey = getApiKey();

        OpenDataAPIHelper apiHelper = new OpenDataAPIHelper(apiKey);
        apiHelper.getHours();

    }



    private String getApiKey(){
        /*ApplicationInfo app = null;
        try {
            app = getApplicationContext().getPackageManager().getApplicationInfo(getApplicationContext().getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Bundle metaData = app.metaData;
        return metaData.getString("openDataKeyValue");*/
        return "f1ed13694973a4e2c10aa8b37308e567";
    }
}