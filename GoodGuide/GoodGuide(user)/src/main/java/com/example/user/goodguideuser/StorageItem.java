package com.example.user.goodguideuser;

import android.graphics.drawable.Drawable;

//저장목록 아이템 클래스
public class StorageItem {
    private Drawable icon;
    private String tour_name;
    private String time;
    private String price;

    public Drawable getIcon() {
        return icon;
    }
    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
    public String getTourName() {
        return tour_name;
    }
    public void setTourName(String tour_name) {
        this.tour_name = tour_name;
    }
    public String getTime(){
        return  time;
    }
    public void setTime(String time){
        this.time = time;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
}
