package com.example.vicky.imagehide;

public class User_Images {

    String userId;
    String image_title;
    String image_date;

    public User_Images(){


    }

    public User_Images(String userId , String image_title , String image_date){

           this.userId = userId;
           this.image_title = image_title;
           this.image_date = image_date;

    }

    public String getUserId() {
        return userId;
    }

    public String getImage_title() {
        return image_title;
    }

    public String getImage_date() {
        return image_date;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setImage_title(String image_title) {
        this.image_title = image_title;
    }

    public void setImage_date(String image_date) {
        this.image_date = image_date;
    }
}
