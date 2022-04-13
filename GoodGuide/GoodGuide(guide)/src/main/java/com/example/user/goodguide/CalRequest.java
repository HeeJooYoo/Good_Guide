package com.example.user.goodguide;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//가이드 가능 여부 데이터베이스 저장
public class CalRequest extends StringRequest {
    final static private String URL = "http://210.179.97.55:55555/GoodGuide/GuideCalRegister.php";
    private Map<String, String> parameters;

    public  CalRequest(String tourID, String tourCal, String guide_possible, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("tourID", tourID);
        parameters.put("tourCal", tourCal);
        parameters.put("guide_possible", guide_possible);

    }

    public Map<String, String> getParams() {
        return parameters;
    }
}

