package com.example.user.goodguideuser;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class ReviewRegisterRequest extends StringRequest {
    final static private String URL = "http://210.179.97.55:55555/GoodGuide/ReviewRegister.php";
    private Map<String, String> parameters;

    public ReviewRegisterRequest(String TuserID, String tourID, String grade, String review, String date, Response.Listener<String> Listener) {
        super(Method.POST, URL, Listener, null);

        parameters = new HashMap<>();
        parameters.put("TuserID", TuserID);
        parameters.put("tourID", tourID);
        parameters.put("grade", grade);
        parameters.put("review", review);
        parameters.put("date", date);
    }

    @Override
    public Map<String, String> getParams() {return parameters;}
}
