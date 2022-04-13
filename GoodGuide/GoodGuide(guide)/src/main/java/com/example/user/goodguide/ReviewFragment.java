package com.example.user.goodguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

//평점 및 리뷰 창 (id:06)
public class ReviewFragment extends Fragment{
    private ListView mListView;
    private ArrayList<ReviewItem> reviewList;
    private ReviewAdapter adapter;
    ReviewItem rItem;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        mListView = (ListView)view.findViewById(R.id.listView);
        reviewList = new ArrayList<ReviewItem>();

        LoginActivity loginActivity = new LoginActivity();
        final String guideID = loginActivity.guideID;

        final Response.Listener responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    response = response.trim();
                    JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    JSONArray reviewListData = jsonResponse.getJSONArray("reviewListData");

                    String tourName, grade, count, tourID;
                    for (int i = 0; i < reviewListData.length(); i++) {
                        JSONObject object = reviewListData.getJSONObject(i);
                        tourName = object.getString("tourName");
                        grade = object.getString("avg");
                        count = object.getString("count");
                        tourID = object.getString("tourID");

                        float fgrade = Float.parseFloat(grade);
                        String sgrade = String.format("%.1f", fgrade);
                        rItem = new ReviewItem(tourName, sgrade, count, tourID);
                        reviewList.add(rItem);
                    }
                    adapter = new ReviewAdapter(getContext(), reviewList);
                    mListView.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        ReviewListRequest reviewListRequest = new ReviewListRequest(guideID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(reviewListRequest);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), Review_Detail_Activity.class);
                intent.putExtra("tourID",adapter.getItem(i).getTourID());
                intent.putExtra("rating",adapter.getItem(i).getGrade());
                intent.putExtra("tourName",adapter.getItem(i).getTourName());
                startActivity(intent);
            }
        });

        return view;
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
}