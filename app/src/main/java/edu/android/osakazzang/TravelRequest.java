package edu.android.osakazzang;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SUT on 2017-09-28.
 */

public class TravelRequest extends StringRequest{

    final static private String URL = "http://audrms11061.cafe24.com/UserLogin.php";

    private Map<String ,String > parameters;

    public TravelRequest(String name, String adress, String phone, String open, String close,
                         Response.Listener<String> listener){
        super(Request.Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("tableName", name);
        parameters.put("tableAdress", adress);
        parameters.put("tablePhone", phone);
        parameters.put("tableOpen", open);
        parameters.put("tableclose", close);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }

}
