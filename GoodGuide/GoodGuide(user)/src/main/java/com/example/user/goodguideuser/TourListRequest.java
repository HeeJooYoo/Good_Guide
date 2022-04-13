package com.example.user.goodguideuser;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class TourListRequest extends StringRequest {
    final static private String URL = "http://210.179.97.55:55555/GoodGuide/RecycleTourList.php";
    private Map<String, String> parameters;

    public TourListRequest(Response.Listener<String> Listener) {
        super(Method.POST, URL, Listener, null);

        parameters = new HashMap<>();
    }

    @Override
    public Map<String, String> getParams() {return parameters;}
}