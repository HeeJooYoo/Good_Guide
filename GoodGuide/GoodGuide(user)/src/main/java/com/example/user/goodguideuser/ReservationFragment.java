package com.example.user.goodguideuser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


//정다은 ppt p41 나의 여행 창
public class ReservationFragment extends Fragment {
    private ListView mListView;
    private ArrayList<ReservationItem> tourList;
    private ReservationAdapter adapter;
    ReservationItem rItem;
    String TuserID;
    public static String tourID;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservation, container, false);

        mListView = (ListView) view.findViewById(R.id.tourList);
        tourList = new ArrayList<ReservationItem>();

        LoginActivity loginActivity = new LoginActivity();
        TuserID = loginActivity.userID;

        final Response.Listener responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    response = response.trim();
                    JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    JSONArray reservData = jsonResponse.getJSONArray("reservData");

                    String tourName, reserv_Date, tour_ID, pNum, price, total;
                    for (int i = 0; i < reservData.length(); i++) {
                        JSONObject object = reservData.getJSONObject(i);
                        tourName = object.getString("tourName");
                        reserv_Date = object.getString("date");
                        tour_ID = object.getString("tourID");
                        pNum = object.getString("pNum");
                        price = object.getString("price");
                        total = object.getString("total");

                        rItem = new ReservationItem(tourName, reserv_Date, tour_ID, pNum, price, total);
                        tourList.add(rItem);
                    }
                    adapter = new ReservationAdapter(getContext(), tourList);
                    mListView.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        MyTripListRequest tourListRequest = new MyTripListRequest(TuserID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(tourListRequest);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ReserveInfoActivity.class);
                intent.putExtra("date",adapter.getItem(i).getDate());
                intent.putExtra("tourName",adapter.getItem(i).getTourName());
                intent.putExtra("pNum",adapter.getItem(i).getPNum());
                intent.putExtra("price",adapter.getItem(i).getPrice());
                intent.putExtra("total",adapter.getItem(i).getTotal());
                intent.putExtra("tourID",adapter.getItem(i).getTourID());
                startActivity(intent);
            }
        });
        return view;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}