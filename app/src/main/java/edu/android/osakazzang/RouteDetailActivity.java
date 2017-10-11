package edu.android.osakazzang;

import android.content.Intent;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RouteDetailActivity extends AppCompatActivity {

    private TextView textView;
    private String id, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_detail);

        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        id = intent.getStringExtra("id");

        textView = (TextView) findViewById(R.id.textView_routeDetail);
        makeData();
//        textView.setText(date + " | " + id);
    }

    public void makeData() {
        Log.i("logTag", "****route makeData()");
        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    if (response.length() > 0 && response.charAt(response.length() - 1) == ',') {
                        response = response.substring(0, response.length() - 1);
                    }

                    response += "]";
                    Log.i("logTag", "****route response : " + response);

                    JSONArray jsonResponse = new JSONArray(response);

                    StringBuffer buffer = new StringBuffer();
                    Log.i("logTag", "****route jsonResponse.length() : " + jsonResponse.length());
                    for (int i = 0; i < jsonResponse.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonResponse.get(i);
                        Log.i("logTag", "****route jsonObject : " + jsonObject.toString());
                        String name = (String) jsonObject.get("SIGHTNAME");
                        String address = (String) jsonObject.get("SIGHTADDRESS");
                        String phone = (String) jsonObject.get("SIGHTPHONE");

                        buffer.append(name).append("\t(").append(phone).append(")\n")
                                .append(address).append("\n\n");
                    }

                    textView.setText(buffer.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        RouteRequest routeRequest = new RouteRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(routeRequest);
    }

    class RouteRequest extends StringRequest {
        private static final String URL = "http://audrms11061.cafe24.com/routeDetail.php";
        private Map<String, String> parameters;

        public RouteRequest(Response.Listener<String> listener) {
            super(Method.POST, URL, listener, null);
        }

        @Override
        protected Map<String, String> getParams() {
            parameters = new HashMap<>();
            Log.i("logTag", "****id : " + id);
            parameters.put("NAME", id);
            parameters.put("DATE", date);
            return parameters;
        }

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            params.put("Content-Type", "application/x-www-form-urlencoded");

            return params;
        }
    }
}
