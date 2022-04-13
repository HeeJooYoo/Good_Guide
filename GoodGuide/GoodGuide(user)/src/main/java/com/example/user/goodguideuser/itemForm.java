package com.example.user.goodguideuser;

import android.graphics.Bitmap;

//리사이클러뷰 사용을 위한 Getter, Setter클래스
public class itemForm {
    private String name;//보안을 위해서 private로
    private Bitmap imageNumber;
    private String time;
    private String money;
    private String tourID;

    //투어 상품 목록에서 클릭하면 투어아이디 가져와야돼서 tourID추가함. 내가 틀릴수도 있으니까 필요없으면 뺴도되고...
    public itemForm(String name1, Bitmap imageNumber1, String time1, String money1, String tourID){ //new생성자를 통해서 생성자가 만들어진다.
        this.name = name1;
        this.imageNumber = imageNumber1;
        this.time = time1;
        this.money = money1;
        this.tourID = tourID;
    }
    public String getName(){//외부로 text값을 리턴해서 내보내준다.
        return name;
    }
    public Bitmap getImageNumber(){//외부로 이미지 값을 리턴해서 보내준다.
        return imageNumber;
    }
    public String getTime(){
        return time;
    }
    public String getMoney(){
        return money;
    }
    public String getTourID(){
        return tourID;
    }
    public void setName(String name1){//외부에서 받은 text를 내부로 넣어준다.
        this.name = name1;
    }
    public void setImageNumber(Bitmap imageNumber1){//외부에서 받은 imagenumber를 내부로 넣어준다.
        this.imageNumber = imageNumber1;
    }
    public void setTime(String time1){
        this.time = time1;
    }
    public void setMoney(String money1){
        this.money = money1;
    }
    public void setTourID(String tourID){
        this.tourID = tourID;
    }
}
