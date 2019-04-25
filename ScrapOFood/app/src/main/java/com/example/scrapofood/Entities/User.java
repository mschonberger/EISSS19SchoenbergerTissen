package com.example.scrapofood.Entities;

public class User {
    public User(String user){
        name = user;
    }
    private String name;
    public String getName(){
        return name;
    }

    private String place;
    public String getPlace(){
        return place;
    }
}
