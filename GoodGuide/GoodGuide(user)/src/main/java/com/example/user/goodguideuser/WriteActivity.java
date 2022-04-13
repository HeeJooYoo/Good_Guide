package com.example.user.goodguideuser;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class WriteActivity extends AppCompatActivity {
    private AlertDialog dialog;
    String grade, tourID, review, date, tourName;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //툴바에 홈버튼 생김
        getSupportActionBar().setTitle("후기 및 평점 작성");

        final TextView tv = (TextView) findViewById(R.id.tvGrade);
        final RatingBar rb = (RatingBar) findViewById(R.id.rtBar);
        final Button btnWrite = findViewById(R.id.btn_write);
        final EditText edtReview = findViewById(R.id.edtReview);
        builder = new AlertDialog.Builder(WriteActivity.this, R.style.AppCompatAlertDialogStyle);

        Intent intent = getIntent();
        tourID = intent.getExtras().getString("tourID");
        date = intent.getExtras().getString("date");
        LoginActivity loginActivity = new LoginActivity();
        final String TuserID = loginActivity.userID;

        final Response.Listener responseListener1 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    response = response.trim();
                    JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    boolean success = jsonResponse.getBoolean("success");

                    if(success) {
                        String grade = jsonResponse.getString("grade");
                        String review = jsonResponse.getString("review");

                        tv.setText(grade);
                        rb.setRating(Float.parseFloat(grade));
                        edtReview.setText(review);
                        btnWrite.setEnabled(false);
                        edtReview.setEnabled(false);
                        rb.setEnabled(false);
                        btnWrite.setBackgroundColor(Color.GRAY);
                    }else{
                        dialog = builder.setMessage("리뷰 작성은 한번만 가능합니다.\n수정할 수 없으니 신중하게 작성해주세요.")
                                .setPositiveButton("확인",null)
                                .create();
                        dialog.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        ReviewValidateRequest reviewValidateRequest = new ReviewValidateRequest(TuserID, tourID, date, responseListener1);
        RequestQueue queue1 = Volley.newRequestQueue(WriteActivity.this);
        queue1.add(reviewValidateRequest);

        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                tv.setText("평점 : " + rating);
                grade = String.valueOf(rating);
            }
        });

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                review = edtReview.getText().toString();

                final Response.Listener responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            response = response.trim();
                            JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            boolean success = jsonResponse.getBoolean("success");

                            if(success) {
                                builder.setMessage("리뷰등록 성공");
                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }else{
                                builder.setMessage("리뷰등록 실패");
                                builder.setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                ReviewRegisterRequest reviewRegisterRequest = new ReviewRegisterRequest(TuserID, tourID, grade, review, date, responseListener);
                RequestQueue queue = Volley.newRequestQueue(WriteActivity.this);
                queue.add(reviewRegisterRequest);
            }
        });
    }
}