package com.example.user.goodguide;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

//투어 정보 등록을 위한 클래스
public class TourRegisterRequest extends StringRequest {
    final static  private  String URL = "http://210.179.97.55:55555/GoodGuide/TourRegister.php";
    private Map<String, String> parameters;

    public TourRegisterRequest(String tourID, String tourName, String tourPrice, String tourCity,
               String tourCountry, String minNum, String maxNum, String meetPlace, String meetTime,
               String estTime, String mapImage, String guideID, Response.Listener<String> listener){
       super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("tourID", tourID);
        parameters.put("tourName", tourName);
        parameters.put("tourPrice", tourPrice);
        parameters.put("tourCity", tourCity);
        parameters.put("tourCountry", tourCountry);
        parameters.put("minNum", minNum);
        parameters.put("maxNum", maxNum);
        parameters.put("meetPlace", meetPlace);
        parameters.put("meetTime", meetTime);
        parameters.put("estTime", estTime);
        parameters.put("mapImage", mapImage);
        parameters.put("guideID", guideID);
    }

    public Map<String, String> getParams() {
        return parameters;
    }
}
