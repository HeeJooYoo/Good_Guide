package com.example.user.goodguideuser;

//나의 여행 목록 각각의 아이템을 지정하기 위한 클래스
public class MyTripItem {
    private String tourName;
    private String reservDate;

    public MyTripItem(String tourName, String reservDate){
        this.tourName = tourName;
        this.reservDate = reservDate;
    }
    public String getTourName(){
        return tourName;
    }
    public String getReservDate(){
        return reservDate;
    }
    public void setTourName(String tourName){
        this.tourName = tourName;
    }
    public void setReservDate(String reservDate){
        this.reservDate = reservDate;
    }
}
