package edu.android.osakazzang;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2017-09-26.
 */

public class MemberRequest extends StringRequest {

    private static final String URL = "http://audrms11061.cafe24.com/testquery.php";
    private String userID;

    private Map<String, String> parameters;

    public MemberRequest(String userID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        this.userID = userID;
    }

    @Override
    public Map<String, String> getParams(){
        parameters = new HashMap<>();
        parameters.put("userID", userID);

        return parameters;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String,String> params = new HashMap<String, String>();
        params.put("Content-Type","application/x-www-form-urlencoded");
        return params;
    }
}
