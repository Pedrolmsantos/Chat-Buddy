package com.example.socialapp;

import android.graphics.Bitmap;

public class SaveInstant {
    private static SaveInstant save;
    private String name;
    private String email;
    private Bitmap photo;
    private String age;
    private String phone;
    private String pwd;
    private int id;
    private SaveInstant(){
    }
    public static SaveInstant getInstance(){
        if(save==null) save = new SaveInstant();
        return save;
    }
    public void setN(String name){
        this.name = name;
    }
    public void setE(String email){
        this.email = email;
    }
    public void setPwd(String pwd){
        this.pwd = pwd;
    }
    public String getPwd(){
        return pwd;
    }
    public String getE(){
        return email;
    }
    public String getN(){
        return name;
    }
    public void setImage(Bitmap photo){

        this.photo = photo;
    }
    public Bitmap getImage(){
        return photo;
    }
    public String getAge(){
        return age;
    }
    public void setAge(String age){
        this.age = age;
    }
    public String getPhone(){
        return phone;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
}
