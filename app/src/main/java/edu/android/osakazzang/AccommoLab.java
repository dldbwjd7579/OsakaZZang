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
 * Created by YuJung on 2017-09-21.
 */

public class AccommoLab {//숙박 Dummy Data Lab Class

    private Context context;
    private String sightName;
    private String sightAddress;
    private String sightPhone;
    private String sightHomepage;
    private int sightOpentime;
    private int sightClosetime;
    private double sightLat;
    private double sightLng;

    private List<Accommo> accommoList;
    private static AccommoLab instance;

    private AccommoLab() {
        accommoList = new ArrayList<>();
    }

    public static AccommoLab getInstance(Context context) {
        if (instance == null) {
            Log.i("logTag", "instance == null  =>  new instance()");
            instance = new AccommoLab();
            instance.context = context;
            instance.makeData();
        }
        return instance;
    }

    public List<Accommo> getAccommoList() {
        return accommoList;
    }


    public void makeData() {
        Log.i("logTag", "accommo makeData()");
        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    if (response.length() > 0 && response.charAt(response.length() - 1) == ',') {
                        response = response.substring(0, response.length() - 1);
                    }

                    response += "]";
                    Log.i("logTag", "accommo response : " + response);

                    JSONArray jsonResponse = new JSONArray(response);

                    StringBuffer buffer = new StringBuffer();
                    Log.i("logTag", "jsonResponse.length() : " + jsonResponse.length());
                    for (int i = 0; i < jsonResponse.length(); i++) {

                        JSONObject jsonObject = (JSONObject) jsonResponse.get(i);

                        sightName = jsonObject.getString("SIGHTNAME");
                        sightAddress = jsonObject.getString("SIGHTADDRESS");
                        sightPhone = jsonObject.getString("SIGHTPHONE");
                        sightLng = jsonObject.getDouble("SIGHTLNG");
                        sightLat = jsonObject.getDouble("SIGHTLAT");

                        Accommo a = new Accommo(R.drawable.icon_hotel, sightName, sightPhone, sightAddress, 100, sightLat, sightLng);
                        accommoList.add(a);
                    }

                    Log.i("logTag", "list size : " + accommoList.size());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        HotelRequest hotelRequest = new HotelRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(hotelRequest);
    }
}

class HotelRequest extends StringRequest{
    private static final String URL = "http://audrms11061.cafe24.com/selectMainDBbyhotel.php";
    private Map<String, String> parameters;

    public HotelRequest(Response.Listener<String> listener) {
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
