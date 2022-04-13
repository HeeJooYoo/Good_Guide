package com.example.user.goodguideuser;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//투어 정보를 불러들이기 위한 요청자 클래스
public class TourInfoRequest extends StringRequest {
    final static  private  String URL = "http://210.179.97.55:55555/GoodGuide/UTourInfo.php";
    private Map<String, String> parameters;

    public TourInfoRequest(String tourID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("tourID", tourID);
    }

    public Map<String, String> getParams() {
        return parameters;
    }
}
