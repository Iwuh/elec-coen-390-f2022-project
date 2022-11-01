package com.teamI.librarymonitoring.retrofitclasses;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface OpenDataApi {

    @Headers("api-key: " + "f1ed13694973a4e2c10aa8b37308e567")
    @GET("/library/hours/{date}")
    Call<List<Hours>> getHours(@Path("date") String date);

}
