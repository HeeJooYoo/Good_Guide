package com.example.user.goodguideuser;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RevervInfoRequest extends StringRequest {
    final static private String URL = "http://210.179.97.55:55555/GoodGuide/ReservInfo.php";
    private Map<String, String> parameters;

    public RevervInfoRequest(String tourID, String TuserID, String date, String pNum, String price, String total, Response.Listener<String> Listener) {
        super(Method.POST, URL, Listener, null);

        parameters = new HashMap<>();
        parameters.put("tourID", tourID);
        parameters.put("TuserID", TuserID);
        parameters.put("date", date);
        parameters.put("pNum", pNum);
        parameters.put("price", price);
        parameters.put("total", total);
    }

    @Override
    public Map<String, String> getParams() {return parameters;}
}