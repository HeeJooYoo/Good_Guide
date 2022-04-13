package com.example.user.goodguide;

public class BookerItem {
    private String userName;
    private String pNum;

    public BookerItem(String userName, String pNum){
        this.userName = userName;
        this.pNum = pNum;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPNum(){
        return  pNum;
    }
    public void setPNum(String pNum){
        this.pNum = pNum;
    }
}
