package com.example.user.goodguideuser;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//회원가입시 중복아이디를 체크하기 위한 클래스
public class ValidateRequest extends StringRequest {
    final static private String URL = "http://210.179.97.55:55555/GoodGuide/TUserValidate.php";
    private Map<String, String> parameters;

    public ValidateRequest(String TuserID, Response.Listener<String> Listener) {
        super(Method.POST, URL, Listener, null);

        parameters = new HashMap<>();
        parameters.put("TuserID", TuserID);
    }

    @Override
    public Map<String, String> getParams() {return parameters;}
}
