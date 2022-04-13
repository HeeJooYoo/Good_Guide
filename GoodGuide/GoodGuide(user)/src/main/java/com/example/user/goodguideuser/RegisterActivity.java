package com.example.user.goodguideuser;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {


    private AlertDialog dialog;
    private boolean validate = false;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("회원가입");
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("회원가입");

        final EditText etID = findViewById(R.id.etID);
        final EditText etPassword = findViewById(R.id.etPW);
        final EditText etPWCheck = findViewById(R.id.etPWCheck);
        final EditText etName = findViewById(R.id.etName);
        final EditText etNumber = findViewById(R.id.etNumber);
        final EditText etAddress = findViewById(R.id.etAddress);
        final Button btnValidate = findViewById(R.id.btnValidate);

        builder = new AlertDialog.Builder(RegisterActivity.this, R.style.AppCompatAlertDialogStyle);
        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String TuserID = etID.getText().toString();

                if(TuserID.trim().equals(null)){
                    dialog = builder.setMessage("아이디를 입력해주세요.")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    validate = false;
                    etID.setFocusable(true);
                }
                if(validate) return;
                final Response.Listener responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean TnewID = jsonResponse.getBoolean("TnewID");

                            if(TnewID){
                                dialog = builder.setMessage("사용 가능한 아이디입니다.")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                                etID.setEnabled(false);
                                etID.setBackgroundColor(Color.GRAY);
                                btnValidate.setBackgroundColor(Color.GRAY);
                                validate = true;
                            } else {
                                dialog = builder.setMessage("중복된 아이디입니다.")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(TuserID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);
            }
        });
        (findViewById(R.id.btnLogin)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TuserID = etID.getText().toString();
                String TuserPassword = etPassword.getText().toString();
                String TuserPWCheck = etPWCheck.getText().toString();
                String TuserName = etName.getText().toString();
                String TuserNumber = etNumber.getText().toString();
                String TuserAddress = etAddress.getText().toString();

                if(!validate){
                    dialog = builder.setMessage("중복체크를 해주세요.")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                if(TuserID.equals("") || TuserPassword.equals("") || TuserPWCheck.equals("") || TuserName.equals("") || TuserNumber.equals("") || TuserAddress.equals("")){
                    dialog = builder.setMessage("모든 정보 입력")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                if (!TuserPassword.equals(TuserPWCheck)) {
                    dialog = builder.setMessage("비밀번호 불일치")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success) {
                                builder.setMessage("회원가입 성공");
                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }else{
                                dialog = builder.setMessage("회원가입 실패")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest  registerRequest = new RegisterRequest(TuserID, TuserPassword, TuserName, TuserNumber, TuserAddress, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
    protected  void onStop(){
        super.onStop();

        if(dialog != null){
            dialog.dismiss();
            dialog = null;
        }
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return (super.onOptionsItemSelected(item));
    }
}