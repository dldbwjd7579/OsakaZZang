package edu.android.osakazzang;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YuJung on 2017-09-18.
 */

public class PlaceLab { //관광지 Dummy Data Lab Class

    private Context context;
    private String sightName;
    private String sightAddress;
    private String sightPhone;
    private String sightHomepage;
    private int sightOpentime;
    private int sightClosetime;
    private double sightLat;
    private double sightLng;

    private List<Place> placeList;
    private static PlaceLab instance;
    private PlaceLab(){
        placeList = new ArrayList<>();
    }
    public static PlaceLab getInstance(Context context){
        if(instance == null){
            instance = new PlaceLab();
            instance.context = context;
            instance.makeData();
        }
        return  instance;
    }
    public List<Place> getPlaceList(){
        return placeList;
    }


    public void makeData() {
        Log.i("logTag", "--sight makeData()");
        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {



                    if (response.length() > 0 && response.charAt(response.length() - 1) == ',') {
                        response = response.substring(0, response.length() - 1);
                    }
                    response += "]";


                    Log.i("logTag", "--sight response : " + response);

                    JSONArray jsonResponse = new JSONArray(response);

                    StringBuffer buffer = new StringBuffer();
                    Log.i("logTag", "--sight jsonResponse.length() : " + jsonResponse.length());
                    for (int i = 0; i < jsonResponse.length(); i++) {

                        JSONObject jsonObject = (JSONObject) jsonResponse.get(i);

                        sightName = jsonObject.getString("SIGHTNAME");
                        sightAddress = jsonObject.getString("SIGHTADDRESS");
                        sightPhone = jsonObject.getString("SIGHTPHONE");
                        sightLng = jsonObject.getDouble("SIGHTLNG");
                        sightLat = jsonObject.getDouble("SIGHTLAT");

                        Place a = new Place(R.drawable.carrier, sightName, sightPhone, sightAddress, 100, sightLat, sightLng);
                        placeList.add(a);
                    }

                    Log.i("logTag", "--sight list size : " + placeList.size());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        SightRequest sightRequest = new SightRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(sightRequest);
    }
}

class SightRequest extends StringRequest {
    private static final String URL = "http://audrms11061.cafe24.com/selectMainDBbythesight.php";
    private Map<String, String> parameters;

    public SightRequest(Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
    }

    @Override
    protected Map<String, String> getParams() {
        parameters = new HashMap<>();
        //parameters.put("SIGHTDIVISION", sightDivision);

        return parameters;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put("Content-Type", "application/x-www-form-urlencoded");

        return params;
    }
}
