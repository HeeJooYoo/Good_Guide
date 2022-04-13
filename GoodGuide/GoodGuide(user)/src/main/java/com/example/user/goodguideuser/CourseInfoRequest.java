package com.example.user.goodguideuser;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CourseInfoRequest extends StringRequest {
    final static  private  String URL = "http://210.179.97.55:55555/GoodGuide/DetailInfo.php";
    private Map<String, String> parameters;

    public CourseInfoRequest(String tourID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("tourID", tourID);
    }

    public Map<String, String> getParams() {
        return parameters;
    }
}
