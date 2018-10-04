package com.example.sayantan.instaapp;

public class Insta {
    private String desc,title,img;

    public Insta(){

    }

    public Insta(String title,String desc,String img){
        this.title = title;
        this.desc = desc;
        this.img = img;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}

