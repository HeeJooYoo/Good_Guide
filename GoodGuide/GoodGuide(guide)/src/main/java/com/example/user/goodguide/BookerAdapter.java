package com.example.user.goodguide;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class BookerAdapter extends BaseAdapter {
    private Context context;
    ArrayList<BookerItem> bookerItems;

    public BookerAdapter(Context context, ArrayList<BookerItem> bookerItems){
        this.context = context;
        this.bookerItems = bookerItems;
    }

    @Override
    public int getCount() {
        return bookerItems.size();
    }

    @Override
    public BookerItem getItem(int position) {
        return bookerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView.inflate(context, R.layout.listview_booker, null);

        TextView userName = (TextView) v.findViewById(R.id.userName);
        TextView pNum = (TextView) v.findViewById(R.id.pNum);

        userName.setText(bookerItems.get(position).getUserName());
        pNum.setText(bookerItems.get(position).getPNum());

        return v;
    }
}
