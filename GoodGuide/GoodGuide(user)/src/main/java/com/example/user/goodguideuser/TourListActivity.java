package com.example.user.goodguideuser;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

//투어 목록 창
public class TourListActivity extends AppCompatActivity {
    RecyclerView rcv;
    GridLayoutManager sgm;
    WritingAdapter wadapter;
    EditText search_Et;
    ArrayList<itemForm> list;
    itemForm rItem;
    Bitmap btmimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_list);
        getSupportActionBar().hide();

        findViewById(R.id.back_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); //뒤로가기 이미지 클릭하면 뒤로가기
            }
        });

        Intent intent = getIntent();
        String search = intent.getStringExtra("search");

        final DecimalFormat myFormatter = new DecimalFormat("###,###");

        rcv = (RecyclerView)findViewById(R.id.article_part);
        sgm = new GridLayoutManager(this, 2);//그리드 레이아웃 사용
        rcv.setHasFixedSize(true);//각 아이템이 보여지는 것을 일정하게
        rcv.setLayoutManager(sgm);//앞서 선언한 리싸이클러뷰를 레이아웃메니저에 붙인다
        search_Et = findViewById(R.id.search_Et);

        list = new ArrayList<>();//itemForm에서 받게되는 데이터를 어레이 리스트화 시킨다.

        final Response.Listener responseListener1 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    response = response.trim();
                    JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    JSONArray tourData = jsonResponse.getJSONArray("tourData");

                    String tourName, tourImage, tour_ID, estTime, price, toformatPrice;
                    for (int i = 0; i < tourData.length(); i++) {
                        JSONObject object = tourData.getJSONObject(i);
                        tourName = object.getString("tourName");
                        estTime = object.getString("estTime");
                        price = object.getString("tourPrice");
                        int toPrice = Integer.parseInt(price);
                        toformatPrice = myFormatter.format(toPrice);
                        tour_ID = object.getString("tourID");
                        tourImage = object.getString("tourImage");
                        imgoutput(tourImage);

                        rItem = new itemForm(tourName, btmimg, estTime, toformatPrice, tour_ID);
                        list.add(rItem);
                    }
                    wadapter = new WritingAdapter(TourListActivity.this, list);
                    rcv.setAdapter(wadapter);// 만든 객체를 리싸이클러뷰에 적용시킨다.
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        SearchTourRequest tourListRequest1 = new SearchTourRequest(search, responseListener1);
        RequestQueue queue1 = Volley.newRequestQueue(getApplicationContext());
        queue1.add(tourListRequest1);
    }

    private class ImgOutPut extends AsyncTask<String, String, Bitmap> {
        Context context;
        HttpURLConnection conn = null;
        Bitmap btm;

        public ImgOutPut(Context context) {
            this.context = context;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.disconnect();
                InputStream is = conn.getInputStream();
                btm = BitmapFactory.decodeStream(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return btm;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
        }
    }

    public void imgoutput(String imgName) {
        String urlString = "http://210.179.97.55:55555/GoodGuide/newImage/"+imgName;
        try {
            TourListActivity.ImgOutPut imgoutput = new TourListActivity.ImgOutPut(getApplicationContext());
            btmimg = imgoutput.execute(urlString).get();
        } catch (Exception e) {
            Log.d("Test","out");
        }
    }
}
