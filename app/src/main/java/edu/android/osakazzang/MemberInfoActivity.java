package edu.android.osakazzang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MemberInfoActivity extends AppCompatActivity {

    private static final String TAG = "edu.android";
    private String userid, useremail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_info);

        final TextView textViewid = (TextView) findViewById(R.id.textView_id);
        final TextView textViewemail = (TextView) findViewById(R.id.textView_email);

        Intent intent = getIntent();
        userid = intent.getStringExtra("name");


        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i(TAG, "response: " + response);
                    JSONObject jsonResponse = new JSONObject(response);

                    userid = jsonResponse.getString("userID");
                    useremail = jsonResponse.getString("userEmail");
                    textViewemail.setText(useremail);
                    textViewid.setText(userid);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };


        MemberRequest memberRequest = new MemberRequest(userid, responseListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(memberRequest);



    }

}
