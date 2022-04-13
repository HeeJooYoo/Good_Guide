package com.example.user.goodguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class BookerListActivity extends AppCompatActivity {
    private ListView mListView;
    private ArrayList<BookerItem> bookerList;
    private BookerAdapter adapter;
    BookerItem rItem;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booker_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String tourID = intent.getStringExtra("tourID");
        String tourName = intent.getStringExtra("tourName");
        String date = intent.getStringExtra("date");
        getSupportActionBar().setTitle(tourName);

        mListView = (ListView)findViewById(R.id.listviewbooker);
        bookerList = new ArrayList<BookerItem>();

//        LoginActivity loginActivity = new LoginActivity();
//        final String guideID = loginActivity.guideID;

        final Response.Listener responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    response = response.trim();
                    JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    JSONArray bookerListData = jsonResponse.getJSONArray("bookerListData");

                    String userName, pNum;
                    for (int i = 0; i < bookerListData.length(); i++) {
                        JSONObject object = bookerListData.getJSONObject(i);
                        userName = object.getString("userName");
                        pNum = object.getString("pNum");

                        rItem = new BookerItem(userName, pNum);
                        bookerList.add(rItem);
                    }
                    adapter = new BookerAdapter(getApplicationContext(), bookerList);
                    mListView.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        BookerInfoRequest bookerInfoRequest = new BookerInfoRequest(tourID, date, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(bookerInfoRequest);
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
