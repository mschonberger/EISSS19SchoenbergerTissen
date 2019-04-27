package com.example.scrapofood.Entities;

import java.util.ArrayList;

public class Group {
    public Group(String name) {
        Users = new ArrayList();
        groupName = name;
    }

    public void addUser(User u){
        Users.add(u);
    }

    public ArrayList getUsers() {
        return Users;
    }

    private ArrayList Users;

    private String groupName;
    public String getName(){ return groupName; }
}
