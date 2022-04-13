package com.example.user.goodguide;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class UserReviewAdapter extends BaseAdapter {
    private Context context;
    ArrayList<UserReviewItem> userReviewItems;

    public UserReviewAdapter(Context context, ArrayList<UserReviewItem> userReviewItems){
        this.context = context;
        this.userReviewItems = userReviewItems;
    }
    @Override
    public int getCount() {
        return userReviewItems.size();
    }

    @Override
    public UserReviewItem getItem(int position) {
        return userReviewItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView.inflate(context, R.layout.listview_user_review, null);

        TextView userName = (TextView) v.findViewById(R.id.userName);
        TextView grade = (TextView) v.findViewById(R.id.grade);
        TextView review = (TextView) v.findViewById(R.id.review);

        userName.setText(userReviewItems.get(position).getUserName());
        grade.setText(userReviewItems.get(position).getGrade());
        review.setText(userReviewItems.get(position).getReview());

        return v;
    }
}
