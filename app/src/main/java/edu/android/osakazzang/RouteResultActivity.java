package edu.android.osakazzang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteResultActivity extends AppCompatActivity {

    private String[] data;
    public static String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_result);

        final Intent intent = getIntent();
        id = intent.getStringExtra("id");
        data = intent.getStringArrayExtra("dataList");
        Log.i("logTag", "====Activity의 dataList : " + data);

        final TextView textView = (TextView)findViewById(R.id.textView3);

        ListView tripListView = (ListView)findViewById(R.id.tripListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        tripListView.setAdapter(adapter);
        tripListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intentt = new Intent(RouteResultActivity.this, RouteDetailActivity.class);
                intentt.putExtra("date", data[i]);
                intentt.putExtra("id", id);
                startActivity(intentt);
            }
        });
    }

}
