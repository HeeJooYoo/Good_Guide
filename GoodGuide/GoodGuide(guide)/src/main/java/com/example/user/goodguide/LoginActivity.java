package com.example.user.goodguide;

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

public class LoginActivity extends AppCompatActivity {
    private AlertDialog dialog;
    public static String guideID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        (findViewById(R.id.btn_login_makeaccount)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        final EditText etID = findViewById(R.id.edt_login_email);
        final EditText etPassword = findViewById(R.id.edt_login_pw);
        final Button btn_login = findViewById(R.id.btn_login_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userID = etID.getText().toString();
                final String userPassword = etPassword.getText().toString();

                if(userID.equals("") || userPassword.equals("")){
                    Toast.makeText(LoginActivity.this, "아이디와 비밀번호를 입력하세요",Toast.LENGTH_SHORT).show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            response = response.trim();
                            JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            boolean success = jsonResponse.getBoolean("success");
                            String loginID = jsonResponse.getString("userID");

                            if (success) {
                                Toast.makeText(LoginActivity.this, "로그인 성공",Toast.LENGTH_SHORT).show();
                                guideID = loginID;
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.AppCompatAlertDialogStyle);
                                dialog = builder.setMessage("계정을 확인하세요")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue (LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null){
            dialog.dismiss();
            dialog = null;
        }
    }
}