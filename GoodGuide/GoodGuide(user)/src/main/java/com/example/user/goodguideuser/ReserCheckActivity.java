package com.example.user.goodguideuser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.DecimalFormat;

//조정민 ppt p37 예약 확인창
public class ReserCheckActivity extends AppCompatActivity {
    String TuserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reser_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //툴바에 홈버튼 생김
        getSupportActionBar().setTitle("예약하기");

        final DecimalFormat myFormatter = new DecimalFormat("###,###");

        final ReservationActivity reservationActivity = (ReservationActivity)ReservationActivity.rActivity;
        Button btnOK = findViewById(R.id.btnOK);
        TextView edtDate = findViewById(R.id.edtDate2);
        TextView edtNum = findViewById(R.id.edtNum2);
        TextView edtPrice = findViewById(R.id.edtPrice2);
        TextView edtTotal = findViewById(R.id.edtTotal2);

        Intent intent = getIntent();
        final String date = intent.getExtras().getString("chooseDate");
        final String pNum = intent.getExtras().getString("peopleNum");
        final String tourID = intent.getExtras().getString("tourID");
        final String price;
        String dateSplit[] = date.split("-");
        String dateString = dateSplit[0] + "년 " + dateSplit[1] + "월 " + dateSplit[2] + "일";

        DetailTourActivity detailTourActivity = new DetailTourActivity();
        price = detailTourActivity.tourPrice;
        int Price = Integer.parseInt(price);
        String formatPrice = myFormatter.format(Price);
        LoginActivity loginActivity = new LoginActivity();
        TuserID = loginActivity.userID;
        edtDate.setText(dateString);
        edtNum.setText(pNum + "명");
        edtPrice.setText(formatPrice+ "원");
        final int total = Integer.parseInt(pNum) * Integer.parseInt(price);
        String formatTotal = myFormatter.format(total);
        edtTotal.setText(formatTotal + "원");

        //투어상세보기창을 다시 보여주는 이벤트처리 + DB에 넣기
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            response = response.trim();
                            JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                Toast.makeText(ReserCheckActivity.this, "예약 성공",Toast.LENGTH_SHORT).show();
                                finish();
                                reservationActivity.finish();
                            } else {
                                Toast.makeText(ReserCheckActivity.this, "예약 실패",Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                //TODO 투어아이디 사용자 아이디 받아오기 구현
                RevervInfoRequest revervInfoRequest = new RevervInfoRequest(tourID, TuserID, date, pNum, price, String.valueOf(total), responseListener);
                RequestQueue queue = Volley.newRequestQueue (ReserCheckActivity.this);
                queue.add(revervInfoRequest);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}