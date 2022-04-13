package com.example.user.goodguide;

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

public class AddressChange extends AppCompatActivity {  //주소 수정
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addresschange);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //툴바에 홈버튼 생김
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.btn_close); //홈버튼의 이미지 변경

        final EditText etAddress = findViewById(R.id.edit_address);
        final Button addressButton = (Button)findViewById(R.id.addresschange);


        ProfileFragment profileFragment = new ProfileFragment();
        String address = profileFragment.guideAddress;
        final String userAddress = address;
        etAddress.setText(userAddress);

        addressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity loginActivity = new LoginActivity();
                String id = loginActivity.guideID;
                final String userID = id;
                final String userAddress = etAddress.getText().toString();

                Response.Listener responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddressChange.this);
                                builder.setMessage("정보수정 완료");
                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });
                                builder.show();

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddressChange.this);
                                dialog = builder.setMessage("정보수정 실패")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                AddressRequest addressRequest = new AddressRequest(userID, userAddress, responseListener);
                RequestQueue queue = Volley.newRequestQueue(AddressChange.this);
                queue.add(addressRequest);
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