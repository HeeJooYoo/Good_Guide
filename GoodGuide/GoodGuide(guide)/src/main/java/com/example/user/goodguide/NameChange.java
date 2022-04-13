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

public class NameChange extends AppCompatActivity {
    private AlertDialog dialog;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.namechange);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //툴바에 홈버튼 생김
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.btn_close); //홈버튼의 이미지 변경

        final EditText etName = findViewById(R.id.edit_name);
        final Button nameButton = (Button) findViewById(R.id.namechange);
        builder = new AlertDialog.Builder(NameChange.this, R.style.AppCompatAlertDialogStyle);

        ProfileFragment profileFragment = new ProfileFragment();
        String name = profileFragment.guideName;
        final String userName = name;
        etName.setText(userName);

        nameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity loginActivity = new LoginActivity();
                String id = loginActivity.guideID;
                final String userID = id;
                final String userName = etName.getText().toString();

                Response.Listener responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(NameChange.this);
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
                NameRequest nameRequest = new NameRequest(userID,userName,responseListener);
                RequestQueue queue = Volley.newRequestQueue(NameChange.this);
                queue.add(nameRequest);
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
