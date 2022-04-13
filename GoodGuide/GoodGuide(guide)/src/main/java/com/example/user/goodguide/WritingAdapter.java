package com.example.user.goodguide;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WritingAdapter extends RecyclerView.Adapter<WritingAdapter.MyViewholder> {
    private Activity activity;
    private ArrayList<itemForm> datalist;
    public static String tourID;

    public WritingAdapter(Activity activity, ArrayList<itemForm> datalist){
        this.activity = activity;
        this.datalist = datalist;
    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        MyViewholder viewholder1 = new MyViewholder(view);
        return viewholder1;
    }

    @Override
    public void onBindViewHolder(MyViewholder holder, int position) {
        itemForm data = datalist.get(position);
        holder.profile.setImageBitmap(data.getImageNumber());
        holder.name.setText(data.getName());
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder
    {
        ImageView profile;
        TextView name;

        public MyViewholder(final View itemview){
            super(itemview);

            profile = (ImageView) itemview.findViewById(R.id.image);
            name = (TextView) itemview.findViewById(R.id.name);

            itemview.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    itemForm data = datalist.get(getAdapterPosition());
                    tourID = data.getTourID();
                    String tourName = data.getName();
                    Intent intent = new Intent(v.getContext(), CalActivity.class);
                    intent.putExtra("tourID", tourID);
                    intent.putExtra("tourName", tourName);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
