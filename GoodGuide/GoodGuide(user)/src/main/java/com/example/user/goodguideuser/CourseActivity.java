package com.example.user.goodguideuser;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

//조정민 ppt p34 코스소개창
public class CourseActivity extends AppCompatActivity {
    int[] img_array = {R.id.course_img1, R.id.course_img2, R.id.course_img3, R.id.course_img4, R.id.course_img5};
    int[] place_array = {R.id.course_name1, R.id.course_name2, R.id.course_name3, R.id.course_name4, R.id.course_name5};
    int[] content_array = {R.id.course_content1, R.id.course_content2, R.id.course_content3, R.id.course_content4, R.id.course_content5};
    ImageView[] course_img = new ImageView[5];
    TextView[] tv_place = new TextView[5];
    TextView[] tv_content = new TextView[5];

    int Detail_num;
    Bitmap btmimg[];

    public String tourID, tourNum[];
    String[] tourPlace, tourImage, tourExplain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("코스 소개");

        Intent intent = getIntent();
        tourID = intent.getStringExtra("tourID");

        tourNum = new String[5]; tourPlace = new String[5];
        tourImage = new String[5]; tourExplain = new String[5];
        btmimg = new Bitmap[5];

        for (int i = 0; i < 5; i++) {
            tv_place[i] = findViewById(place_array[i]);
            course_img[i] = findViewById(img_array[i]);
            tv_content[i] = findViewById(content_array[i]);
        }

        final Response.Listener responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    response = response.trim();
                    JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    JSONArray tourDetailData = jsonResponse.getJSONArray("tourDetailData");

                    Detail_num = tourDetailData.length();

                    for (int i = 0; i < tourDetailData.length(); i++) {
                        tv_place[i].setVisibility(View.VISIBLE);
                        course_img[i].setVisibility(View.VISIBLE);
                        tv_content[i].setVisibility(View.VISIBLE);

                        JSONObject object = tourDetailData.getJSONObject(i);
                        tourNum[i] = object.getString("tourNum");
                        tourPlace[i] = object.getString("tourPlace");
                        tourImage[i] = object.getString("tourImage");
                        tourExplain[i] = object.getString("tourExplain");

                        tv_place[i].setText(tourPlace[i]);
                        tv_content[i].setText(tourExplain[i]);
                        imgoutput(tourImage[i],i);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        CourseInfoRequest detailinfo = new CourseInfoRequest(tourID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(CourseActivity.this);
        queue.add(detailinfo);
    }

    private class ImgOutPut extends AsyncTask<String, String, Bitmap> {
        Context context;
        HttpURLConnection conn = null;
        int num;

        public ImgOutPut(Context context) {
            this.context = context;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                num = Integer.valueOf(strings[1]) ;
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.disconnect();
                InputStream is = conn.getInputStream();
                btmimg[num] = BitmapFactory.decodeStream(is);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return btmimg[num];
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            for (int i=0; i < Detail_num;i++) {
                course_img[i].setImageBitmap(btmimg[i]);
            }
        }
    }

    public void imgoutput(String imgName, int i) {
        int num = i;
        String urlString = "http://210.179.97.55:55555/GoodGuide/newImage/"+imgName;
        try {
            ImgOutPut imgoutput = new ImgOutPut(CourseActivity.this);
            imgoutput.execute(urlString,String.valueOf(num));
        } catch (Exception e) {
            Log.d("Test","out");
        }
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
