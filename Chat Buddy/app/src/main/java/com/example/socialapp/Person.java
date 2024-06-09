package com.example.socialapp;

import android.graphics.Bitmap;
import android.os.Parcelable;

public class Person {
    private int id;
    private String name;
    private String email;
    private String password;
    private String age;
    private String phone;


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
    public int getId(){
        return id;
    }

    public String getEmail(){
        return email;
    }

    public void setId(int id){
        this.id = id;
    }
    public String getName(){
        return name;
    }

    public String getPassword(){
        return password;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }


}


