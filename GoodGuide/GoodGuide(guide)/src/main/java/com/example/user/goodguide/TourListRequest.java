package com.example.user.goodguide;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class TourListRequest extends StringRequest {
    final static  private  String URL = "http://210.179.97.55:55555/GoodGuide/TourList.php";
    private Map<String, String> parameters;

    public  TourListRequest(String guideID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("guideID", guideID);
    }

    public Map<String, String> getParams() {
        return parameters;
    }
}
