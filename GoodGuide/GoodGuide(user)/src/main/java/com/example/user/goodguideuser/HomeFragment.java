package com.example.user.goodguideuser;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import java.text.DecimalFormat;
import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private ArrayList<itemForm> list, list2;
    private WritingAdapter wadapter, wadapter2;
    itemForm rItem;
    RecyclerView rcv;
    String a;
    Bitmap btmimg;
    RecyclerView rcv2;
    DecimalFormat myFormatter = new DecimalFormat("###,###");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        LinearLayoutManager horizonalLayoutManager;

        rcv = (RecyclerView) view.findViewById(R.id.article_part1);
        horizonalLayoutManager = new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL,false);
        rcv.setHasFixedSize(true);//각 아이템이 보여지는 것을 일정하게
        rcv.setLayoutManager(horizonalLayoutManager);//앞서 선언한 리싸이클러뷰를 레이아웃메니저에 붙인다
        horizonalLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        final EditText edtSearch = view.findViewById(R.id.et_search);

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Intent intent = new Intent(getContext(), TourListActivity.class);
                    intent.putExtra("search", edtSearch.getText().toString().trim());
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        list = new ArrayList<>();//itemForm에서 받게되는 데이터를 어레이 리스트화 시킨다.
        final Response.Listener responseListener = new Response.Listener<String>() {
            Bitmap bitmap;
            @Override
            public void onResponse(String response) {
                try {
                    response = response.trim();
                    JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    JSONArray famoustourData = jsonResponse.getJSONArray("famoustourData");

                    String tourName, tourImage, tour_ID, estTime, price,formatPrice;
                    for (int i = 0; i < famoustourData.length(); i++) {
                        JSONObject object = famoustourData.getJSONObject(i);
                        tourName = object.getString("tourName");
                        estTime = object.getString("estTime");
                        price = object.getString("price");
                        int Price = Integer.parseInt(price);
                        formatPrice = myFormatter.format(Price);
                        tour_ID = object.getString("tourID");
                        tourImage = object.getString("tourImage");
                        imgoutput(tourImage);

                        rItem = new itemForm(tourName, btmimg, estTime, formatPrice, tour_ID);
                        list.add(rItem);
                    }
                    wadapter = new WritingAdapter(getActivity(), list);
                    rcv.setAdapter(wadapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        FamousTourListRequest famousTourListRequest = new FamousTourListRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(famousTourListRequest);

        //여기까지가 한부분

        GridLayoutManager horizonalLayoutManager2;

        rcv2 = (RecyclerView) view.findViewById(R.id.article_part2);
        horizonalLayoutManager2 = new GridLayoutManager(getContext(), 2);
        rcv2.setHasFixedSize(true);//각 아이템이 보여지는 것을 일정하게
        horizonalLayoutManager2.setReverseLayout(true);
        rcv2.setLayoutManager(horizonalLayoutManager2);//앞서 선언한 리싸이클러뷰를 레이아웃메니저에 붙인다
        horizonalLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);


        list2 = new ArrayList<>();//itemForm에서 받게되는 데이터를 어레이 리스트화 시킨다.

        final Response.Listener responseListener1 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    response = response.trim();
                    JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    JSONArray tourData = jsonResponse.getJSONArray("tourData");

                    String tourName, tourImage, tour_ID, estTime, price,toformatPrice;
                    for (int i = 0; i < tourData.length(); i++) {
                        JSONObject object = tourData.getJSONObject(i);
                        tourName = object.getString("tourName");
                        estTime = object.getString("estTime");
                        price = object.getString("price");
                        int toPrice = Integer.parseInt(price);
                        toformatPrice = myFormatter.format(toPrice);
                        tour_ID = object.getString("tourID");
                        tourImage = object.getString("tourImage");
                        imgoutput(tourImage);

                        rItem = new itemForm(tourName, btmimg, estTime, toformatPrice, tour_ID);
                        list2.add(rItem);
                    }
                    wadapter2 = new WritingAdapter(getActivity(), list2);
                    rcv2.setAdapter(wadapter2);// 만든 객체를 리싸이클러뷰에 적용시킨다.
                    rcv2.scrollToPosition(wadapter2.getItemCount()-1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        TourListRequest tourListRequest1 = new TourListRequest(responseListener1);
        RequestQueue queue1 = Volley.newRequestQueue(getContext());
        queue1.add(tourListRequest1);

        return view;
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
            ImgOutPut imgoutput = new ImgOutPut(getContext());
            btmimg = imgoutput.execute(urlString).get();
        } catch (Exception e) {
            Log.d("Test","out");
        }
    }


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
}