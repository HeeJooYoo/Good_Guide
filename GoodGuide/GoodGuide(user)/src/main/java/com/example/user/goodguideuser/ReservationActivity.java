package com.example.user.goodguideuser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

//조정민 ppt p36 예약창
public class ReservationActivity extends AppCompatActivity {
    String str = null; //tv_people 값을 저장
    int num, reservNum; //tv_people 값을 변환하기 위한 int형 변수 (str 변수를 int형으로 변환)
    public static Activity rActivity;
    ListView listView;
    SimpleAdapter adapter;
    String tourID;
    Date nowdate, dbDate;
    SimpleDateFormat sdf;
    String chooseDate, peopleNum;
    private AlertDialog dialog;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        builder = new AlertDialog.Builder(ReservationActivity.this, R.style.AppCompatAlertDialogStyle);

        rActivity = ReservationActivity.this;
        chooseDate = "";
        Button plus = findViewById(R.id.plus);
        Button minus = findViewById(R.id.minus);
        final Button people_check = findViewById(R.id.people_check);
        final TextView people_num = findViewById(R.id.people_num);
        listView = findViewById(R.id.TourCalList);

        Intent intent = getIntent();
        tourID = intent.getStringExtra("tourID");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("예약하기");

        Long now = System.currentTimeMillis();
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        nowdate = new Date(now);

        final ArrayList<HashMap<String, String>> calList = new ArrayList<>();
        String[] valueArray = new String[]{"cal", "tourID"};
        int[] viewForm = new int[]{android.R.id.text1, android.R.id.text2};

        final Response.Listener responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    response = response.trim();
                    JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    JSONArray guideCalList = jsonResponse.getJSONArray("calData");

                    reservNum = guideCalList.length();
                    for (int i = 0; i < guideCalList.length(); i++) {
                        HashMap<String, String> item = new HashMap<String, String>();
                        JSONObject object = guideCalList.getJSONObject(i);
                        dbDate = sdf.parse(object.getString("tourCal")); //날짜 비교를 위한 형변환
                        if(nowdate.getTime() < dbDate.getTime()){
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

        CalListRequest guideValidateRequest = new CalListRequest(tourID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ReservationActivity.this);
        queue.add(guideValidateRequest);

        adapter = new SimpleAdapter(getApplicationContext(), calList, android.R.layout.simple_list_item_1, valueArray, viewForm);
        listView.setAdapter(adapter);

        //리스트뷰 클릭 이벤트 처리
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HashMap<String, String> hashID = (HashMap<String, String>) calList.get(position);
                chooseDate = hashID.get("cal");
            }
        });

        //인원 증가 버튼
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = people_num.getText().toString();
                num = Integer.parseInt(str);
                num++;
                str = Integer.toString(num);
                people_num.setText(str);
            }
        });
        //인원 감소 버튼
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = people_num.getText().toString(); //텍스트뷰의 값을 변수에 저장
                num = Integer.parseInt(str); //string을 int으로 변환
                if (num > 1) {  //현재 텍스트뷰의 값이 1보다 크다면
                    num--; //값 내리기
                    str = Integer.toString(num); //int를 string으로 다시 변환
                    people_num.setText(str); //텍스트뷰에 값 넣기
                } else //현재 텍스트뷰의 값이 1보다 작다면
                    Toast.makeText(getApplicationContext(), "최소 인원입니다.", Toast.LENGTH_SHORT).show();  //숫자 변동 없음, 오류 메시지 출력
            }
        });
        //확인 버튼
        people_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reservNum == 0){
                    dialog = builder.setMessage("예약을 할 수 없습니다.")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }else if(chooseDate.equals("")) {
                    dialog = builder.setMessage("날짜를 선택해주세요.")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }else{
                    peopleNum = people_num.getText().toString();
                    Intent intent = new Intent(ReservationActivity.this, ReserCheckActivity.class);
                    intent.putExtra("chooseDate", chooseDate);
                    intent.putExtra("peopleNum", peopleNum);
                    intent.putExtra("tourID", tourID);
                    startActivity(intent);
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
}
