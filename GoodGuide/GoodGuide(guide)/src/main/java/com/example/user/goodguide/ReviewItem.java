package com.example.user.goodguide;

//라뷰 아이템 클래스
public class ReviewItem {
    private String tourName;
    private String grade;
    private String count;
    private String tourID;

    public ReviewItem(String tourName, String grade, String count, String tourID){
        this.tourName = tourName;
        this.grade = grade;
        this.count = count;
        this.tourID = tourID;
    }
    public String getTourName() {
        return tourName;
    }
    public void setTourName(String tour_name) {
        this.tourName = tour_name;
    }
    public String getGrade(){
        return  grade;
    }
    public void setGrade(String grade){
        this.grade = grade;
    }
    public String getCount() {
        return count;
    }
    public void setCount(String count) {
        this.count = count;
    }
    public String getTourID() {
        return tourID;
    }
    public void setTourID(String tourID) {
        this.tourID = tourID;
    }
}
