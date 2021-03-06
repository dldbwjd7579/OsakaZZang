package edu.android.osakazzang;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2017-09-21.
 * 로그인창에서 입력한 Id 와  Password를 서버로 보내는 Request
 */

public class LoginRequest extends StringRequest{


    final static private String URL = "http://audrms11061.cafe24.com/UserLogin.php";

    private Map<String, String> parameters;

    public LoginRequest(String userID, String userPassword, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);


    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }

}
