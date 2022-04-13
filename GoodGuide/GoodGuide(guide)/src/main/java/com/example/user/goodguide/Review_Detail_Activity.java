package com.example.user.goodguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Review_Detail_Activity extends AppCompatActivity {
    private ListView rListView;
    private ArrayList<UserReviewItem> userReviewList;
    private UserReviewAdapter adapter;
    UserReviewItem rItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);

        Intent intent = getIntent();
        String tourID = intent.getStringExtra("tourID");
        String rating = intent.getStringExtra("rating");
        String tourName = intent.getStringExtra("tourName");
        getSupportActionBar().setTitle(tourName);

        rListView = (ListView)findViewById(R.id.listView);
        userReviewList = new ArrayList<UserReviewItem>();
        TextView stringRating = findViewById(R.id.textRating);
        RatingBar intRating = findViewById(R.id.intRating);
        Float fRating = Float.parseFloat(rating);

        stringRating.setText(rating);
        intRating.setRating(Float.parseFloat(rating));

        final Response.Listener responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    response = response.trim();
                    JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    JSONArray reviewListData = jsonResponse.getJSONArray("reviewData");

                    String userName, grade, review;
                    for (int i = 0; i < reviewListData.length(); i++) {
                        JSONObject object = reviewListData.getJSONObject(i);
                        userName = object.getString("TuserName");
                        grade = object.getString("grade");
                        review = object.getString("review");

                        rItem = new UserReviewItem(userName, grade, review);
                        userReviewList.add(rItem);
                    }
                    adapter = new UserReviewAdapter(getApplicationContext(), userReviewList);
                    rListView.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        UserReviewRequest reviewListRequest = new UserReviewRequest(tourID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Review_Detail_Activity.this);
        queue.add(reviewListRequest);

    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return (super.onOptionsItemSelected(item));
    }
}
