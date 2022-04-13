package com.example.user.goodguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.user.goodguide.LoginActivity.guideID;

public class TourFragment extends Fragment {
    public static String tourID;
    SimpleAdapter adapter;
    ArrayList<HashMap<String, String>> tourList;
    ListView listView;
    TextView editText;

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tour, container, false);
        Button button = (Button)view.findViewById(R.id.plus);
        LoginActivity loginActivity = new LoginActivity();
        String id = loginActivity.guideID;
        final String guideID = id;

        listView = view.findViewById(R.id.list_menu);
        editText = view.findViewById(R.id.text1);

        //투어 추가 버튼 클릭 이벤트 / 투어 아이디 부여를 여기서함.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Response.Listener responseListener1 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            response = response.trim();
                            JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));

                            boolean success = jsonResponse.getBoolean("success");
                            int count = jsonResponse.getInt("count"); //로그인한 회원이 등록한 투어의 수를 알려주는 변수

                            if(success) {
                                count = count + 1; //투어아이디 부여를 위해 1을 증가시킴
                                String cnt = Integer.toString(count);
                                tourID = guideID + cnt; //투어아이디는 회원아이디에 숫자를 부여해서 만듦.
                                Intent intent = new Intent(getActivity(),TourRegisterActivity.class);
                                startActivity(intent);
                            }else{
                                Log.d("test1","카운트 실패");
                                return;
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };

                TourIDRequest tourIDRequest = new TourIDRequest(guideID,responseListener1);
                RequestQueue queue1 = Volley.newRequestQueue(getContext());
                queue1.add(tourIDRequest);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HashMap<String, String> hashID = (HashMap<String, String>) tourList.get(position);
                String positionID = hashID.get("tourID");
                String positionName = hashID.get("tourName");
                tourID = positionID;
                Intent intent = new Intent(getActivity(),TourInfoActivity.class);
                intent.putExtra("tourName", positionName);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        onList();
    }

    public void onList(){
        tourList = new ArrayList<>();
        String[] valueArray = new String[]{"tourName", "tourID"};
        int[] viewForm = new int[]{android.R.id.text1, android.R.id.text2};

        //투어 목록 보여주기
        final Response.Listener responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    response = response.trim();
                    JSONArray nameList = new JSONArray(response);

                    int num = nameList.length();
                    if(num != 0){
                        editText.setText("나만의 투어를 수정해보세요.");
                    }else{
                        editText.setText("나만의 투어를 등록해보세요.");
                    }

                    for (int i = 0; i < nameList.length(); i++) {
                        HashMap<String, String> item = new HashMap<String, String>();
                        JSONObject object = nameList.getJSONObject(i);
                        item.put("tourID", object.getString("tourID"));
                        item.put("tourName", object.getString("tourName"));
                        tourList.add(item);
                        adapter.notifyDataSetChanged();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        TourListRequest tourListRequest = new TourListRequest(guideID,responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(tourListRequest);

        adapter = new SimpleAdapter(getContext(), tourList, android.R.layout.simple_list_item_1, valueArray, viewForm);
        listView.setAdapter(adapter);
    }
}

//출처 https://youtu.be/edZwD54xfbk