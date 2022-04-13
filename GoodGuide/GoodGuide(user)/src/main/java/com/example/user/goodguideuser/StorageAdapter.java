package com.example.user.goodguideuser;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

//저장목록 어댑터
public class StorageAdapter extends BaseAdapter {
    /* 아이템을 세트로 담기 위한 어레이 */
    private ArrayList<StorageItem> mItems = new ArrayList<>();

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public StorageItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Context context = parent.getContext();

        /* 'listview_storage' Layout을 inflate하여 convertView 참조 획득 */
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_storage, parent, false);
        }

        /* 'listview_storage'에 정의된 위젯에 대한 참조 획득 */
        ImageView iv_img = (ImageView) convertView.findViewById(R.id.iv_img) ;
        TextView tour_name = (TextView) convertView.findViewById(R.id.tour_name) ;
        TextView time = (TextView) convertView.findViewById(R.id.time) ;
        TextView price = (TextView) convertView.findViewById(R.id.price) ;

        /* 각 리스트에 뿌려줄 아이템을 받아오는데 재활용 */
        StorageItem myItem = getItem(position);

        /* 각 위젯에 세팅된 아이템을 뿌려준다 */
        iv_img.setImageDrawable(myItem.getIcon());
        time.setText(myItem.getTime());
        tour_name.setText(myItem.getTourName());
        price.setText(myItem.getPrice());

        return convertView;
    }

    /* 아이템 데이터 추가를 위한 함수 */
    public void addItem(Drawable img, String tour_name, String time, String price) {

        StorageItem mItem = new StorageItem();

        /* StorageItem에 아이템을 setting한다. */
        mItem.setIcon(img);
        mItem.setTime(time);
        mItem.setTourName(tour_name);
        mItem.setPrice(price);

        /* mItems에 StorageItem을 추가한다. */
        mItems.add(mItem);
    }
}
