package com.example.user.goodguide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

//쪽지 어댑터
public class MyAdapter extends BaseAdapter {

    /* 아이템을 세트로 담기 위한 어레이 */
    private ArrayList<MyItem> mItems = new ArrayList<>();

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public MyItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Context context = parent.getContext();

        /* 'listview_custom' Layout을 inflate하여 convertView 참조 획득 */
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_custom, parent, false);
        }

        /* 'listview_custom'에 정의된 위젯에 대한 참조 획득 */
        ImageView iv_img = (ImageView) convertView.findViewById(R.id.iv_img) ;
        TextView state = (TextView) convertView.findViewById(R.id.state) ;
        TextView name = (TextView) convertView.findViewById(R.id.name) ;
        TextView tour_name = (TextView) convertView.findViewById(R.id.tour_name) ;
        TextView date = (TextView) convertView.findViewById(R.id.date) ;

        /* 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용 */
        MyItem myItem = getItem(position);

        /* 각 위젯에 세팅된 아이템을 뿌려준다 */
        iv_img.setImageDrawable(myItem.getIcon());
        state.setText(myItem.getStates());
        name.setText(myItem.getName());
        tour_name.setText(myItem.getTourName());
        date.setText(myItem.getDate());

        return convertView;
    }

    /* 아이템 데이터 추가를 위한 함수 */
    public void addItem(Drawable img, String state, String name, String tour_name, String date) {

        MyItem mItem = new MyItem();

        /* MyItem에 아이템을 setting한다. */
        mItem.setIcon(img);
        mItem.setStates(state);
        mItem.setName(name);
        mItem.setTourName(tour_name);
        mItem.setDate(date);

        /* mItems에 MyItem을 추가한다. */
        mItems.add(mItem);
    }
}
