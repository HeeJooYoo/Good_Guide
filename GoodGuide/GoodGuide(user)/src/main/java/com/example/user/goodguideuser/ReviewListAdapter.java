package com.example.user.goodguideuser;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ReviewListAdapter extends BaseAdapter {
    private Context context;
    ArrayList<ReviewListItem> reviewListItems;

    public ReviewListAdapter(Context context, ArrayList<ReviewListItem> reviewListItems){
        this.context = context;
        this.reviewListItems = reviewListItems;
    }
    @Override
    public int getCount() {
        return reviewListItems.size();
    }

    @Override
    public ReviewListItem getItem(int position) {
        return reviewListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView.inflate(context, R.layout.listview_tour_review, null);

        TextView userName = (TextView) v.findViewById(R.id.userName);
        TextView grade = (TextView) v.findViewById(R.id.grade);
        TextView review = (TextView) v.findViewById(R.id.review);

        userName.setText(reviewListItems.get(position).getUserName());
        grade.setText(reviewListItems.get(position).getGrade());
        review.setText(reviewListItems.get(position).getReview());

        return v;
    }
}
