package com.example.user.goodguide;

import android.graphics.Bitmap;

public class itemForm {
    private Bitmap imageNumber;
    private String tourName;
    private String tourID;

    public itemForm(Bitmap imageNumber1, String tourName, String tourID){
        this.imageNumber = imageNumber1;
        this.tourName = tourName;
        this.tourID = tourID;
    }
    public Bitmap getImageNumber(){
        return imageNumber;
    }
    public String getName(){
        return tourName;
    }
    public String getTourID(){
        return tourID;
    }

    public void setImageNumber(Bitmap imageNumber1){
        this.imageNumber = imageNumber1;
    }
    public void setName(String tourName){
        this.tourName = tourName;
    }
    public void setTourID(String tourID){
        this.tourID = tourID;
    }
}
