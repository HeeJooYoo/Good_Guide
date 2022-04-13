package com.example.user.goodguide;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

//가이드 가능 여부 선택 및 리스트
public class CalActivity extends AppCompatActivity {
    SimpleAdapter adapter;
    String guide_possible, tourID, tourCal, tourName;
    int select;
    ListView listView;
    SimpleDateFormat sdf;
    Date nowdate, dbDate, tempdate; //오늘 날짜, DB날짜, 클릭된 날짜

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cal);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //CalendarFragment cal = new CalendarFragment();

//        CalendarFragment calendarFragment = new CalendarFragment();
//        tourID = calendarFragment.tourID;
        Intent intent = getIntent();
        tourID = intent.getStringExtra("tourID");
        tourName = intent.getStringExtra("tourName");
        getSupportActionBar().setTitle(tourName);

        CalendarView calView = (CalendarView) findViewById(R.id.calView);
        listView = findViewById(R.id.cal_listView);

        final AlertDialog.Builder oDialog = new AlertDialog.Builder(this,R.style.AppCompatAlertDialogStyle);
        Long now = System.currentTimeMillis();
        nowdate = new Date(now);
        sdf = new SimpleDateFormat("yyyy-MM-dd"); //현재날짜 sdf는 Date 형
        onList();

        calView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                final CharSequence[] question = {"가이드 가능","가이드 불가"}; //가이드 가능이면 0 가이드 불가면 1
                select = 1; //기본값주기
                String m, d;
                if(dayOfMonth > 9 ){
                    m = "-"+dayOfMonth;
                }else{
                    m = "-0" + dayOfMonth;
                }
                if(month > 9 ){
                    d = "-"+(month+1);
                }else{
                    d = "-0"+(month+1);
                }
                tourCal = year + d + m;

                try {
                    tempdate = sdf.parse(tourCal); //날짜 비교를 위한 형변환
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(nowdate.getTime()<tempdate.getTime()){
                    oDialog.setTitle(year+"년 "+(month+1)+"월 "+dayOfMonth+"일").setSingleChoiceItems(question, 1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            select = which;
                        }
                    }).setNegativeButton("취소",null).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            guide_possible = String.valueOf(select);
                            Response.Listener responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response1) {
                                    try{
                                        JSONObject jsonResponse = new JSONObject(response1);
                                        boolean success = jsonResponse.getBoolean("success");
                                        if(success) {
                                            onList();
                                            Toast.makeText(CalActivity.this, "날짜 등록 성공",Toast.LENGTH_SHORT).show();
                                        }else{
                                            onList();
                                            Toast.makeText(CalActivity.this, "날짜 등록 실패",Toast.LENGTH_SHORT).show();
                                        }
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            };
                            CalRequest CalRequest = new CalRequest(tourID, tourCal, guide_possible, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(CalActivity.this);
                            queue.add(CalRequest);
                        }
                    });
                    oDialog.show();
                } else {
                    Toast.makeText(CalActivity.this, "날짜를 선택할 수 없습니다.",Toast.LENGTH_SHORT).show();
                }
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

    public void onList(){
        final ArrayList<HashMap<String, String>> calList = new ArrayList<>();
        String[] valueArray = new String[]{"cal", "tourID"};
        int[] viewForm = new int[]{android.R.id.text1, android.R.id.text2};

        final Response.Listener responseListener1 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    response = response.trim();
                    JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    JSONArray guideCalList = jsonResponse.getJSONArray("calData");

                    for (int i = 0; i < guideCalList.length(); i++) {
                        HashMap<String, String> item = new HashMap<String, String>();
                        JSONObject object = guideCalList.getJSONObject(i);
                        dbDate = sdf.parse(object.getString("tourCal")); //날짜 비교를 위한 형변환
                        if(nowdate.getTime()<dbDate.getTime()){
                            item.put("cal", object.getString("tourCal"));
                            item.put("tourID", object.getString("tourID"));
                            calList.add(item);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        GuideValidateRequest guideValidateRequest = new GuideValidateRequest(tourID, responseListener1);
        RequestQueue queue1 = Volley.newRequestQueue(CalActivity.this);
        queue1.add(guideValidateRequest);

        adapter = new SimpleAdapter(getApplicationContext(), calList, android.R.layout.simple_list_item_1, valueArray, viewForm);
        listView.setAdapter(adapter);
    }
}
