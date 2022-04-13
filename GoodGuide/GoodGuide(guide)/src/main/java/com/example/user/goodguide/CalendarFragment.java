package com.example.user.goodguide;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
import java.util.ArrayList;
import java.util.HashMap;

public class CalendarFragment extends Fragment {
    SimpleAdapter adapter;
    public static String tourID, tourName;
    public int num;
    private ArrayList<itemForm> list;
    private WritingAdapter wadapter;
    itemForm rItem;
    RecyclerView rcv;
    Bitmap btmimg;
    String a;

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        ListView listView = view.findViewById(R.id.cal_listView);
        final TextView txt = view.findViewById(R.id.txt);

        LoginActivity loginActivity = new LoginActivity();
        String id = loginActivity.guideID;
        final String guideID = id;

        //리사이클러뷰 사용 부분
        LinearLayoutManager horizonalLayoutManager;

        rcv = (RecyclerView) view.findViewById(R.id.article_part1);
        horizonalLayoutManager = new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL,false);
        rcv.setHasFixedSize(true);
        rcv.setLayoutManager(horizonalLayoutManager);

        list = new ArrayList<>();//itemForm에서 받게되는 데이터를 어레이 리스트화 시킨다.

        final Response.Listener responseListener = new Response.Listener<String>() {
            Bitmap bitmap;
            @Override
            public void onResponse(String response) {
                try {
                    response = response.trim();
                    JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    JSONArray tourData = jsonResponse.getJSONArray("tourData");

                    num = tourData.length();
                    if(num != 0){
                        txt.setText("투어의 일정 선택이 가능합니다.");
                    }else{
                        txt.setText("투어를 등록하면 일정 선택이 가능합니다.");
                    }

                    String tourImage, tourName, tour_ID;
                    for (int i = 0; i < tourData.length(); i++) {
                        JSONObject object = tourData.getJSONObject(i);
                        tourImage = object.getString("tourImage");
                        tourName = object.getString("tourName");
                        tour_ID = object.getString("tourID");
                        imgoutput(tourImage);

                        rItem = new itemForm(btmimg, tourName, tour_ID);
                        list.add(rItem);
                    }
                    wadapter = new WritingAdapter(getActivity(), list);
                    rcv.setAdapter(wadapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        CalTourListRequest caltourListRequest = new CalTourListRequest(guideID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(caltourListRequest);

        //예약자 목록 보여주기
        final ArrayList<HashMap<String, String>> tourList = new ArrayList<>();
        String[] valueArray = new String[]{"tourName", "date","tourID"};
        int[] viewForm = new int[]{android.R.id.text1, android.R.id.text2};

        final Response.Listener responseListener1 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    response = response.trim();
                    JSONArray nameList = new JSONArray(response);

                    for (int i = 0; i < nameList.length(); i++) {
                        HashMap<String, String> item = new HashMap<String, String>();
                        JSONObject object = nameList.getJSONObject(i);
                        item.put("tourID", object.getString("tourID"));
                        item.put("tourName", object.getString("tourName"));
                        item.put("date", object.getString("date"));
                        tourList.add(item);
                        adapter.notifyDataSetChanged();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        BookerListRequest bookerListRequest = new BookerListRequest(guideID, responseListener1);
        RequestQueue queue1 = Volley.newRequestQueue(getContext());
        queue1.add(bookerListRequest);

        adapter = new SimpleAdapter(getContext(), tourList, android.R.layout.simple_list_item_2, valueArray, viewForm);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HashMap<String, String> hashID = (HashMap<String, String>) tourList.get(position);
                String positionID = hashID.get("tourID");
                String positionName = hashID.get("tourName");
                String date = hashID.get("date");
                tourID = positionID;
                tourName = positionName;
                Intent intent = new Intent(getActivity(),BookerListActivity.class);
                intent.putExtra("tourID", tourID);
                intent.putExtra("tourName", tourName);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });
        return view;
    }
    //이미지 가져오기
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
            ImgOutPut imgoutput = new ImgOutPut(getContext());
            btmimg = imgoutput.execute(urlString).get();
        } catch (Exception e) {
            Log.d("Test","out");
        }
    }
}

