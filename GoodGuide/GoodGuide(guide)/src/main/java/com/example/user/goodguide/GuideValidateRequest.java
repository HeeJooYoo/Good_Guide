package com.example.user.goodguide;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//해당 투어 일정 불러오기
public class GuideValidateRequest extends StringRequest {
    final static private String URL = "http://210.179.97.55:55555/GoodGuide/GuideValidate.php";

    private Map<String, String> parameters;

    public GuideValidateRequest(String tourID, Response.Listener<String> Listener) {
        super(Method.POST, URL, Listener, null);

        parameters = new HashMap<>();
        parameters.put("tourID", tourID);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}

