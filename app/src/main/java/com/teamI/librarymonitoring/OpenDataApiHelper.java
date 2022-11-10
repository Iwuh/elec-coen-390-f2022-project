package com.teamI.librarymonitoring;

import android.content.Context;
import android.os.Build;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.teamI.librarymonitoring.datacontainers.LibraryComputerData;
import com.teamI.librarymonitoring.datacontainers.OccupancyData;
import com.teamI.librarymonitoring.datacontainers.ServiceHours;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


// helper class for the Open Data API
// follow the example of getHours function
// pass a list of objects that will be populated
// pass a listener
// when listener.onResponse() is called, we have a response and we can use the list, which is populated

public class OpenDataApiHelper {


    private RequestQueue queue;
    final static String urlHours = "https://opendata.concordia.ca/API/v1/library/hours/";
    final static String urlComputerUse = "https://opendata.concordia.ca/API/v1/library/computers/";
    final static String urlOccupancy = "https://opendata.concordia.ca/API/v1/library/occupancy/";

    public OpenDataApiHelper(Context context) {
        this.queue = Volley.newRequestQueue(context);
    }

    public void getHours(List<ServiceHours> allServiceHours, final IOpenDataResponseListener listener){

        String url = urlHours + getDate();

        // [] {}
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int k = 0; k < response.length(); k++){
                            JSONObject listItem = new JSONObject();
                            try {
                                ServiceHours serviceHours = new ServiceHours();
                                listItem = response.getJSONObject(k);
                                serviceHours.setService(listItem.getString("service"));
                                serviceHours.setHoursText(listItem.getString("text"));
                                allServiceHours.add(serviceHours);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                        // inform the listener that response has been completed
                        listener.onResponse();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        listener.onError(error.getMessage());
                    }
                })


        {
            // provide authorization info
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getAuthorizationMap();
            }
        };

        queue.add(request);
    }

    private String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date());
    }

    public void getLibraryComputerData(List<LibraryComputerData> lstLibraryComputerData, final IOpenDataResponseListener listener){
        String url = urlComputerUse;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        // each key represents a library name
                        Iterator<String> libraryNames = response.keys();

                        while(libraryNames.hasNext()){
                            String currentLibraryName = libraryNames.next();
                            LibraryComputerData lcData = new LibraryComputerData();
                            try {
                                JSONObject libraryJSONObj = response.getJSONObject(currentLibraryName);
                                // the Desktops attribute has an array of key-value pairs
                                HashMap<String, Integer> hmRoomsToDesktops = new HashMap<String, Integer>();
                                JSONObject desktopsJSONObj = libraryJSONObj.getJSONObject("Desktops");
                                Iterator<String> roomNames = desktopsJSONObj.keys();
                                while(roomNames.hasNext()){
                                    String roomName = roomNames.next();
                                    Integer nComputers = Integer.parseInt(desktopsJSONObj.getString(roomName));

                                    lcData.addPairToDesktopMap(roomName, nComputers);
                                }

                                lcData.setLibraryName(currentLibraryName);
                                lcData.setLaptops(Integer.parseInt(libraryJSONObj.getString("Laptops")));
                                lcData.setTablets(Integer.parseInt(libraryJSONObj.getString("Tablets")));

                                lstLibraryComputerData.add(lcData);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        listener.onResponse();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        listener.onError(error.getMessage());
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders(){
                return getAuthorizationMap();
            }
        };

        queue.add(request);
    }
    public void Occupancy (List<OccupancyData> allOccupancyData, final IOpenDataResponseListener listener){

        String url = urlOccupancy;

        // [] {}
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {



                            try {
                                OccupancyData occupancyDataWebster = new OccupancyData();
                                JSONObject temp =  response.getJSONObject("Webster");
                                occupancyDataWebster.setLibraryName("Webster");
                                occupancyDataWebster.setOccupancy(temp.getString("Occupancy"));
                                occupancyDataWebster.setLastRecordTime(temp.getString("LastRecordTime"));
                                System.out.println(occupancyDataWebster);

                                OccupancyData occupancyDataVanier = new OccupancyData();
                                temp = response.getJSONObject("Vanier");
                                occupancyDataVanier.setLibraryName("Vanier");
                                occupancyDataVanier.setOccupancy(temp.getString("Occupancy"));
                                occupancyDataVanier.setLastRecordTime(temp.getString("LastRecordTime"));
                                System.out.println(occupancyDataVanier);

                                OccupancyData occupancyDataGreyNuns = new OccupancyData();
                                temp = response.getJSONObject("GreyNuns");
                                occupancyDataGreyNuns.setLibraryName("GreyNuns");
                                occupancyDataGreyNuns.setOccupancy(temp.getString("Occupancy"));
                                occupancyDataGreyNuns.setLastRecordTime(temp.getString("LastRecordTime"));
                                System.out.println(occupancyDataGreyNuns);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
       //                     System.out.println(response);



                        // inform the listener that response has been completed
                        listener.onResponse();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        listener.onError(error.getMessage());
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders(){
                return getAuthorizationMap();
            }
        };
        queue.add(request);
        }

    private Map<String, String> getAuthorizationMap(){
        HashMap params = new HashMap<String, String>();
        // needed to suppress error code about API version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params.put("Authorization",
                    String.format("Basic %s", Base64.getEncoder().encodeToString(
                            String.format("%s:%s", BuildConfig.OPEN_DATA_API_USER, BuildConfig.OPEN_DATA_API_KEY).getBytes()
                    )));
        }
        return params;
    }

}
