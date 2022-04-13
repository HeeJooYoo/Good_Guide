package com.example.user.goodguideuser;

import android.content.DialogInterface;
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

public class PhoneChange extends AppCompatActivity {
    private AlertDialog dialog;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phonechange);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //툴바에 홈버튼 생김
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.btn_close); //홈버튼의 이미지 변경

        Button phoneButton = (Button) findViewById(R.id.phonechange);
        final EditText edtPhone = findViewById(R.id.edit_phone);

        ProfileFragment profileFragment = new ProfileFragment();
        final String userPhone = profileFragment.TuserNumber;
        edtPhone.setText(userPhone);
        builder = new AlertDialog.Builder(PhoneChange.this,R.style.AppCompatAlertDialogStyle);

        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity loginActivity = new LoginActivity();
                final String userID = loginActivity.userID;
                final String userPhone = edtPhone.getText().toString();

                Response.Listener responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            response = response.trim();
                            JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                builder.setMessage("정보수정 완료");
                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });
                                builder.show();
                            }else{
                                dialog = builder.setMessage("정보수정 실패")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                PhoneRequest phoneRequest = new PhoneRequest(userID, userPhone, responseListener);
                RequestQueue queue = Volley.newRequestQueue(PhoneChange.this);
                queue.add(phoneRequest);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //버튼의 액션
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
