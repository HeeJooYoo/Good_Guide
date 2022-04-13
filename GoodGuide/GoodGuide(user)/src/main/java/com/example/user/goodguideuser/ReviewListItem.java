package com.example.user.goodguideuser;

public class ReviewListItem {
    private String username;
    private String grade; //평점
    private String review; //리뷰내용

    public ReviewListItem(String username, String grade, String review){
        this.username = username;
        this.grade = grade;
        this.review = review;
    }
    public String getUserName() {
        return username;
    }
    public void setUserName(String username) {
        this.username = username;
    }
    public String getGrade(){
        return  grade;
    }
    public void setGrade(String grade){
        this.grade = grade;
    }
    public String getReview() {
        return review;
    }
    public void setReview(String review) {
        this.review = review;
    }
}
