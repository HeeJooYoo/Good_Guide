package com.example.user.goodguideuser;

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

public class ReviewListActivity extends AppCompatActivity {
    private ListView rListView;
    private ArrayList<ReviewListItem> reviewList;
    private ReviewListAdapter adapter;
    ReviewListItem rItem;
    String tourID, avgString;
    float total = 0, avg = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        setTitle("후기 및 평점");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);

        Intent intent = getIntent();
        tourID = intent.getStringExtra("tourID");
        avgString = intent.getStringExtra("avg");

        rListView = (ListView)findViewById(R.id.listView);
        reviewList = new ArrayList<ReviewListItem>();
        final TextView stringRating = findViewById(R.id.textRating);
        final RatingBar intRating = findViewById(R.id.intRating);

        final Response.Listener responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    response = response.trim();
                    JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    JSONArray reviewListData = jsonResponse.getJSONArray("reviewData");

                    String userName, grade, review;
                    int i;
                    for (i = 0; i < reviewListData.length(); i++) {
                        JSONObject object = reviewListData.getJSONObject(i);
                        userName = object.getString("TuserName");
                        grade = object.getString("grade");
                        review = object.getString("review");

                        total += Float.parseFloat(grade);
                        rItem = new ReviewListItem(userName, grade, review);
                        reviewList.add(rItem);
                    }
                    adapter = new ReviewListAdapter(getApplicationContext(), reviewList);
                    rListView.setAdapter(adapter);

                    stringRating.setText(avgString);
                    intRating.setRating(Float.parseFloat(avgString));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        ReviewListRequest reviewListRequest = new ReviewListRequest(tourID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ReviewListActivity.this);
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
