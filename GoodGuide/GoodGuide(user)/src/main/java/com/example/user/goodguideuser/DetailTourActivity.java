package com.example.user.goodguideuser;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

//김보현 투어 상세보기 창
public class DetailTourActivity extends AppCompatActivity {
    private AlertDialog dialog;
    AlertDialog.Builder builder;
    int flag = 0;
    public static String tourPrice;
    public String tourID;
    String tourName, tourCountry, tourCity, estTime, meetTime, meetPlace, mapImage, minNum, maxNum, avg, formatPrice;
    ImageView img_map;
    Bitmap img, map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tour);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        getSupportActionBar().setTitle(intent.getStringExtra("tourName"));

        final DecimalFormat myFormatter = new DecimalFormat("###,###");

        tourID = intent.getStringExtra("tourID");
        img = intent.getParcelableExtra("img");

        final TextView bigTitletv = findViewById(R.id.bigtitle);
        final TextView smallTitletv = findViewById(R.id.smalltitle);
        final TextView peoplecounttv = findViewById(R.id.peoplecount);
        final TextView pricetv = findViewById(R.id.price);
        final TextView meetTimetv = findViewById(R.id.realtime);
        final TextView estTimetv = findViewById(R.id.taketitme);
        final TextView meetTime2tv = findViewById(R.id.meettime);
        final TextView meetPlacetv = findViewById(R.id.meetplace);
        final TextView meetPlace2tv = findViewById(R.id.meetplace2);
        final TextView avgtv = findViewById(R.id.avg);
        final ImageView imageView = findViewById(R.id.tourimage);
        img_map = findViewById(R.id.gmap);
        Button starBtn = findViewById(R.id.star);
        Button courseBtn = findViewById(R.id.course);
        Button guideBtn = findViewById(R.id.guide);
        Button reservBtn = findViewById(R.id.book);

        final Response.Listener responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    response = response.trim();
                    JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));

                    boolean success = jsonResponse.getBoolean("success");
                    tourName = jsonResponse.getString("tourName");
                    tourPrice = jsonResponse.getString("tourPrice");
                    int Price = Integer.parseInt(tourPrice);
                    formatPrice = myFormatter.format(Price);
                    tourCountry = jsonResponse.getString("tourCountry");
                    tourCity = jsonResponse.getString("tourCity");
                    estTime = jsonResponse.getString("estTime");
                    meetTime = jsonResponse.getString("meetTime");
                    meetPlace = jsonResponse.getString("meetPlace");
                    mapImage = jsonResponse.getString("mapImage");
                    minNum = jsonResponse.getString("minNum");
                    maxNum = jsonResponse.getString("maxNum");
                    avg = jsonResponse.getString("avg");

                    if(success) {
                        imageView.setImageBitmap(img);
                        bigTitletv.setText(tourName);
                        smallTitletv.setText(tourCountry + ", " + tourCity);
                        peoplecounttv.setText("인원 "+ minNum + " - " + maxNum + "명" );
                        pricetv.setText(formatPrice + "원");
                        meetTimetv.setText(meetTime);
                        estTimetv.setText(estTime + "시간");
                        meetTime2tv.setText("만나는 시간 " + meetTime);
                        meetPlacetv.setText("만나는 장소 " + meetPlace);
                        meetPlace2tv.setText("만나는 장소 " + meetPlace);
                        imgoutput(mapImage);
                        Float favg = Float.parseFloat(avg);
                        avg = String.format("%.1f",favg);
                        avgtv.setText(avg);
                    }else{
                        dialog = builder.setMessage("투어 정보가 없습니다.")
                                .setPositiveButton("확인",null)
                                .create();
                        dialog.show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        TourInfoRequest tourInfoRequest = new TourInfoRequest(tourID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(DetailTourActivity.this);
        queue.add(tourInfoRequest);

        //후기 및 평점 버튼
        starBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailTourActivity.this, ReviewListActivity.class);
                intent.putExtra("tourID", tourID);
                intent.putExtra("avg", avg);
                startActivity(intent);
            }
        });
        //코스 소개 버튼
        courseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailTourActivity.this, CourseActivity.class);
                intent.putExtra("tourID", tourID);
                startActivity(intent);
            }
        });
        //안내사항 및 유의사항 버튼
        guideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailTourActivity.this, GuidanceActivity.class);
                startActivity(intent);
            }
        });
        //예약하기 버튼
        reservBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tourPrice = pricetv.getText().toString();
                Intent intent = new Intent(DetailTourActivity.this, ReservationActivity.class);
                intent.putExtra("tourID", tourID);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.heart:
                if(flag == 1) {
                    item.setIcon(R.drawable.ic_favorite_white_24dp);
                    flag =0;
                }else if(flag ==0){
                    item.setIcon(R.drawable.ic_favorite_border_white_24dp);
                    flag =1;
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class ImgOutPut extends AsyncTask<String, String, Bitmap> {
        Context context;
        HttpURLConnection conn = null;

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
                map = BitmapFactory.decodeStream(is);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return map;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            img_map.setImageBitmap(map);
        }
    }

    public void imgoutput(String imgName) {
        String urlString = "http://210.179.97.55:55555/GoodGuide/newImage/" + imgName;
        try {
            ImgOutPut imgoutput = new ImgOutPut(DetailTourActivity.this);
            imgoutput.execute(urlString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}