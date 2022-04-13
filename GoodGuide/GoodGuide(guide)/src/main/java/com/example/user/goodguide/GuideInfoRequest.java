package com.example.user.goodguide;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GuideInfoRequest extends StringRequest {

    final static private String URL = "http://210.179.97.55:55555/GoodGuide/GuideInfo.php";
    private Map<String,String> parameters;

    public GuideInfoRequest(String userID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
    }

    @Override
    public Map<String, String> getParams() {return parameters;}
}
