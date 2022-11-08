package com.teamI.librarymonitoring;

import android.content.Context;
import android.os.Build;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.teamI.librarymonitoring.datacontainers.ServiceHours;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// helper class for the Open Data API
// follow the example of getHours function
// pass a list of objects that will be populated
// pass a listener
// when listener.onResponse() is called, we have a response and we can use the list, which is populated

public class OpenDataApiHelper {


    private RequestQueue queue;

    public OpenDataApiHelper(Context context) {
        this.queue = Volley.newRequestQueue(context);
    }

    public void getHours(List<ServiceHours> allServiceHours, final IOpenDataResponseListener listener){

        String url = "https://opendata.concordia.ca/API/v1/library/hours/" + getDate();
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
        };

        queue.add(request);
    }

    private String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date());
    }

}
