package com.example.appprojet.models;

import java.util.List;

public class Task {
    private String name;
    private List<User> userList;

    public Task(String name, List<User> userList){
        this.name = name;
        this.userList = userList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public String getName() {
        return name;
    }
}
