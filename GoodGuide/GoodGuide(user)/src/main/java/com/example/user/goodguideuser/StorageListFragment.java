package com.example.user.goodguideuser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

//정다은 ppt p38 저장목록창
public class StorageListFragment extends Fragment {
    private ListView mListView;
    private String timeString = "시간";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_storage_list, container, false);

         /* 위젯과 멤버변수 참조 획득 */
        mListView = (ListView)view.findViewById(R.id.listView);

        /* 아이템 추가 및 어댑터 등록 */
        dataSetting();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), DetailTourActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    private void dataSetting(){

        StorageAdapter mMyAdapter = new StorageAdapter();

        mMyAdapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_account_circle_black_24dp), "건축학도에게 듣는 가우디 건축","5" + timeString, "50,000" );

        /* 리스트뷰에 어댑터 등록 */
        mListView.setAdapter(mMyAdapter);
    }

}
