package com.example.user.goodguideuser;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

//나의 여행 어댑터
public class ReservationAdapter extends BaseAdapter {
    private Context context;
    ArrayList<ReservationItem> reservationItems;

    public ReservationAdapter(Context context, ArrayList<ReservationItem> reservationItems){
        this.context = context;
        this.reservationItems = reservationItems;
    }
    @Override
    public int getCount() {
        return reservationItems.size();
    }

    @Override
    public ReservationItem getItem(int position) {
        return reservationItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView.inflate(context, R.layout.listview_reservation, null);

        TextView tourName = (TextView) v.findViewById(R.id.tourName);
        TextView date = (TextView) v.findViewById(R.id.date);
        TextView tourID = (TextView) v.findViewById(R.id.tourID);

        tourName.setText(reservationItems.get(position).getTourName());
        date.setText(reservationItems.get(position).getDate());
        tourID.setText(reservationItems.get(position).getTourID());
        return v;
    }
}
