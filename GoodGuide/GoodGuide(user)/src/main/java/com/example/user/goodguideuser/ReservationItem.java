package com.example.user.goodguideuser;

//나의 여행 아이템 클래스
public class ReservationItem {
    private String tour_name;
    private String date;
    private String tourID;
    private String pNum;
    private String price;
    private String total;

    public ReservationItem(String tour_name, String date, String tourID, String pNum, String price, String total){
        this.tour_name = tour_name;
        this.date = date;
        this.tourID = tourID;
        this.pNum = pNum;
        this.price = price;
        this.total = total;
    }
    public String getTourName() {
        return tour_name;
    }
    public void setTourName(String tour_name) {
        this.tour_name = tour_name;
    }
    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date = date;
    }
    public String getTourID(){
        return tourID;
    }
    public void setTourID(String tourID){
        this.tourID = tourID;
    }
    public String getPNum(){
        return pNum;
    }
    public void setPNum(String pNum){
        this.pNum = pNum;
    }
    public String getPrice(){
        return price;
    }
    public void setPrice(String price){
        this.price = price;
    }
    public String getTotal(){
        return total;
    }
    public void setTotal(String total){
        this.total = total;
    }
}
