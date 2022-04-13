package com.example.user.goodguide;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

//리뷰 어댑터
public class ReviewAdapter extends BaseAdapter {
    private Context context;
    ArrayList<ReviewItem> reviewItems;

    public ReviewAdapter(Context context, ArrayList<ReviewItem> reviewItems){
        this.context = context;
        this.reviewItems = reviewItems;
    }

    @Override
    public int getCount() {
        return reviewItems.size();
    }

    @Override
    public ReviewItem getItem(int position) {
        return reviewItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView.inflate(context, R.layout.listview_review, null);

        TextView tourName = (TextView) v.findViewById(R.id.tour_name);
        TextView grade = (TextView) v.findViewById(R.id.grade);
        TextView count = (TextView) v.findViewById(R.id.count);

        tourName.setText(reviewItems.get(position).getTourName());
        grade.setText(reviewItems.get(position).getGrade());
        count.setText(reviewItems.get(position).getCount());
        return v;
    }
}
