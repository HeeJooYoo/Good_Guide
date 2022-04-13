package com.example.user.goodguideuser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

//유희주 상세검색창 중 인원선택 ppt p31
public class PeopleChooseActivity extends AppCompatActivity {
    TextView tv_people = null;
    String str = null; //tv_people 값을 저장
    int num; //tv_people 값을 변환하기 위한 int형 변수 (str 변수를 int형으로 변환)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_choose);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.btn_close); //홈버튼의 이미지 변경

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.btn_minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_people = findViewById(R.id.tv_people);
                str = tv_people.getText().toString(); //텍스트뷰의 값을 변수에 저장
                num = Integer.parseInt(str); //string을 int으로 변환
                if (num > 1) {  //현재 텍스트뷰의 값이 1보다 크다면
                    num--; //값 내리기
                    str = Integer.toString(num); //int를 string으로 다시 변환
                    tv_people.setText(str); //텍스트뷰에 값 넣기
                }
                else //현재 텍스트뷰의 값이 1보다 작다면
                    Toast.makeText(getApplicationContext(),"최소 인원입니다.", Toast.LENGTH_SHORT).show();  //숫자 변동 없음, 오류 메시지 출력
            }
        });

        findViewById(R.id.btn_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_people = findViewById(R.id.tv_people);
                str = tv_people.getText().toString();
                num = Integer.parseInt(str);
                num++;
                str = Integer.toString(num);
                tv_people.setText(str);
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
