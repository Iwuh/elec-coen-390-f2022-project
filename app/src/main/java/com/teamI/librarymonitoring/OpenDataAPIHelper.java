package com.teamI.librarymonitoring;

/*
This utility class will be used to access the OpenData API.
 */

import com.teamI.librarymonitoring.retrofitclasses.Hours;
import com.teamI.librarymonitoring.retrofitclasses.OpenDataApi;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenDataAPIHelper {

    protected static final String urlString = "https://opendata.concordia.ca/API/v1/";
    protected String apiKey;
    protected Retrofit retrofit;

    public OpenDataAPIHelper(String key)  {
        try{
            apiKey = key;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        retrofit = new Retrofit.Builder().baseUrl(urlString).addConverterFactory(GsonConverterFactory.create()).build();


    }

    public void getHours() {

        // TODO: get this to actually work
        OpenDataApi openDataApi = retrofit.create(OpenDataApi.class);
        // keeps stopping at line below
        Call<List<Hours>> call = openDataApi.getHours(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
        call.enqueue(new Callback<List<Hours>>() {
            @Override
            public void onResponse(Call<List<Hours>> call, Response<List<Hours>> response) {
                if(!response.isSuccessful()){
                    String s = response.message();
                    return;
                }
                List<Hours> hours = response.body();
            }

            @Override
            public void onFailure(Call<List<Hours>> call, Throwable t) {

            }
        });

    }



}
