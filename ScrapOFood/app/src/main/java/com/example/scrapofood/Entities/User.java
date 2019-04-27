package com.example.scrapofood.Entities;

import java.util.List;

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

    private List<Food> offeredFood;
    public List<Food> getFood() { return offeredFood; }

    private List<Group> currentGroups;
    public List<Group> getCurrentGroups(){ return currentGroups; }
}
