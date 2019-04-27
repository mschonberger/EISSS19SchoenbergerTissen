package com.example.scrapofood.Entities;

public class Food {

    public Food(User u,String name){
        owner = u;
        Name = name;
    }
    private User owner;
    public User getOwner(){
        return owner;
    }

    private Integer Range;
    public Integer getRange(){
        return Range;
    }

    private String Name;
    public String getName(){
        return Name;
    }
}
