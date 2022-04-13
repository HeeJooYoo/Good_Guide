package com.example.user.goodguideuser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

//조정민 ppt p35 안내사항창
public class GuidanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidence);

        //TextView guidecontent = findViewById(R.id.guideContent);
        //TextView notice = findViewById(R.id.notice);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("안내사항");

//        guidecontent.setText("01 예약 취소가 어려우니 신중히 예약해 주시기 바랍니다.\n" +
//                "02 투어 상품을 꼼꼼히 읽어보시고 예약하시기 바랍니다.\n" +
//                "03 본 투어는 한국어로만 진행됩니다.\n");
//        notice.setText("01 투어 당일에 제시된 금액만큼 현금을 준비해 오시기 바랍니다.\n" +
//                "02 제시된 금액을 준비하지 못할 경우 투어에 참여할 수 없으니 주의하시기 바랍니다.\n" +
//                "03 예약은 약속입니다. 약속된 시간과 장소에 꼭 참여바랍니다.\n");

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
