package com.example.user.goodguideuser;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    final static  private  String URL = "http://210.179.97.55:55555/GoodGuide/TUserRegister.php";
    private Map<String, String> parameters;

    public RegisterRequest(String TuserID, String TuserPassword, String TuserName, String TuserNumber,
                           String TuserAddress, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("TuserID", TuserID);
        parameters.put("TuserPassword", TuserPassword);
        parameters.put("TuserName", TuserName);
        parameters.put("TuserNumber", TuserNumber);
        parameters.put("TuserAddress", TuserAddress);
    }

    public Map<String, String> getParams() {
        return parameters;
    }
}
