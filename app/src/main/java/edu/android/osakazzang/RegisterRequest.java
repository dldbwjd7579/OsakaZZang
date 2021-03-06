package edu.android.osakazzang;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by myunggeun on 2017-09-21.
 * 회원가입 액티비티에서 입력받은 정보를 서버로 보내는 RegisterRequest
 */

public class RegisterRequest extends StringRequest {

    final static private String URL = "http://audrms11061.cafe24.com/UserRegister.php";

    private Map<String, String> parameters;

    public RegisterRequest(String userID, String userPassword, String userEmail, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userEmail", userEmail);


    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }


}
