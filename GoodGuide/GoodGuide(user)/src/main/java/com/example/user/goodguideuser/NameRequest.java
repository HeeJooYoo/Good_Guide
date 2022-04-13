package com.example.user.goodguideuser;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class NameRequest extends StringRequest {
    final static  private  String URL = "http://210.179.97.55:55555/GoodGuide/TNameUpdate.php";
    private Map<String, String> parameters;

    public NameRequest(String TuserID, String TuserName, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();//초기화
        parameters.put("TuserID", TuserID);
        parameters.put("TuserName", TuserName);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
