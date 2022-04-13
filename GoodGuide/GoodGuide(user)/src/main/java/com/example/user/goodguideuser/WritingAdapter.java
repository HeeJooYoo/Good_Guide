package com.example.user.goodguideuser;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
    public Bitmap img;

    //getItemCount, onCreateViewHolder, MyViewHolder, onBindViewholder 순으로 들어오게 된다.
    // 뷰홀더에서 초기세팅해주고 바인드뷰홀더에서 셋텍스트해주는 값이 최종적으로 화면에 출력되는 값
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
        holder.name.setText(data.getName());
        holder.profile.setImageBitmap(data.getImageNumber());
        holder.time.setText(data.getTime()+"시간");
        holder.money.setText(data.getMoney() + "원");
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder
    {
        ImageView profile;
        TextView time;
        TextView name;
        TextView money;

        public MyViewholder(final View itemview){
            super(itemview);

            profile = (ImageView) itemview.findViewById(R.id.image);
            name = (TextView) itemview.findViewById(R.id.name);
            time = (TextView) itemview.findViewById(R.id.time);
            money = (TextView) itemview.findViewById(R.id.money);

            itemview.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    itemForm data = datalist.get(getAdapterPosition());
                    tourID = data.getTourID();
                    String tourName = data.getName();
                    img = data.getImageNumber();
                    Intent intent = new Intent(v.getContext(), DetailTourActivity.class);
                    intent.putExtra("tourID", tourID);
                    intent.putExtra("tourName", tourName);
                    intent.putExtra("img", img);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
