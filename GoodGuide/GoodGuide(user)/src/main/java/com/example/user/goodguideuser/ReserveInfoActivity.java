package com.example.user.goodguideuser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

//정다은 ppt p42 예약 정보창
public class ReserveInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_info);

        final DecimalFormat myFormatter = new DecimalFormat("###,###");

        Intent intent = getIntent();
        String tourName = intent.getExtras().getString("tourName");
        final String date = intent.getExtras().getString("date");
        String pNum = intent.getExtras().getString("pNum");
        String price = intent.getExtras().getString("price");
        int Price = Integer.parseInt(price);
        String formatPrice = myFormatter.format(Price);
        String total = intent.getExtras().getString("total");
        int totalPrice = Integer.parseInt(total);
        String totalformatPrice = myFormatter.format(totalPrice);
        final String tourID = intent.getExtras().getString("tourID");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //툴바에 홈버튼 생김
        getSupportActionBar().setTitle(tourName);

        Button btnDetail = findViewById(R.id.btnDetail);
        Button btnWrite = findViewById(R.id.btnWrite);
        TextView tvDate = findViewById(R.id.edtDate2);
        TextView tvPNum = findViewById(R.id.edtNum2);
        TextView tvPrice = findViewById(R.id.edtPrice2);
        TextView tvTotal = findViewById(R.id.edtTotal2);
        String dateSplit[] = date.split("-");
        String dateString = dateSplit[0] + "년 " + dateSplit[1] + "월 " + dateSplit[2] + "일";

        tvDate.setText(dateString);
        tvPNum.setText(pNum+"명");
        tvPrice.setText(formatPrice+"원");
        tvTotal.setText(totalformatPrice+"원");

        //투어 상세보기 버튼
        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReserveInfoActivity.this, DetailTourActivity.class);
                intent.putExtra("tourID", tourID);
                startActivity(intent);
            }
        });
        //후기 작성 버튼
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReserveInfoActivity.this, WriteActivity.class);
                intent.putExtra("tourID", tourID);
                intent.putExtra("date", date);
                startActivity(intent);
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