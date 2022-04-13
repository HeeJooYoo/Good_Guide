package com.example.user.goodguide;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddressRequest extends StringRequest {
    final static  private  String URL = "http://210.179.97.55:55555/GoodGuide/AddressUpdate.php";
    private Map<String, String> parameters;

    public AddressRequest(String userID, String userAddress, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();//초기화
        parameters.put("userID", userID);
        parameters.put("userAddress", userAddress);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}