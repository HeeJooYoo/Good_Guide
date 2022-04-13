package com.example.user.goodguide;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Detail_Update_Request extends StringRequest {
    final static  private  String URL = "http://210.179.97.55:55555/GoodGuide/DetailUpdateIN.php";
    private Map<String, String> parameters;

    public Detail_Update_Request(String tourID, String tourNum, String tourPlace, String tourImage, String tourExplain, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("tourID", tourID);
        parameters.put("tourNum", tourNum);
        parameters.put("tourPlace", tourPlace);
        parameters.put("tourImage", tourImage);
        parameters.put("tourExplain", tourExplain);
    }

    public Map<String, String> getParams() {
        return parameters;
    }
}
