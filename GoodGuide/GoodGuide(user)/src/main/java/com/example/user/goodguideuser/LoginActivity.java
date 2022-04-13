package com.example.user.goodguideuser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

//로그인창
public class LoginActivity extends AppCompatActivity {
    private AlertDialog dialog;
    public static String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        (findViewById(R.id.btn_login_makeaccount)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        final EditText etID = findViewById(R.id.edt_login_email);
        final EditText etPassword = findViewById(R.id.edt_login_pw);
        final Button btn_login = findViewById(R.id.btn_login_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String TuserID = etID.getText().toString();
                final String TuserPassword = etPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            String loginID = jsonResponse.getString("TuserID");

                            if (success) {
                                Toast.makeText(LoginActivity.this, "로그인 성공",Toast.LENGTH_SHORT).show();
                                userID = loginID;
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "로그인 실패",Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(TuserID, TuserPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue (LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }

}