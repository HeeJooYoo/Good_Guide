package com.example.user.goodguide;

import android.graphics.drawable.Drawable;

//쪽지 아이템 클래스
public class MyItem {
    private Drawable icon; private String tour_name;
    private String date; private String state; private String name;

    public Drawable getIcon() {
        return icon;
    }
    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
    public String getStates(){
        return  state;
    }
    public void setStates(String state){
        this.state = state;
    }
    public String getName(){
        return  name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getTourName() {
        return tour_name;
    }
    public void setTourName(String tour_name) {
        this.tour_name = tour_name;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
