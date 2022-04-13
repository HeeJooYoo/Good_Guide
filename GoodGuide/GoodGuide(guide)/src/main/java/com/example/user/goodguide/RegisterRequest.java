package com.example.user.goodguide;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    final static  private  String URL = "http://210.179.97.55:55555/GoodGuide/UserRegister.php";
    private Map<String, String> parameters;

    public  RegisterRequest(String userID, String userPassword, String userName, String userNumber,
                            String userAddress, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userName", userName);
        parameters.put("userNumber", userNumber);
        parameters.put("userAddress", userAddress);
    }

    public Map<String, String> getParams() {
        return parameters;
    }
}
