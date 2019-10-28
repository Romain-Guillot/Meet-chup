package com.example.appprojet.models;

import java.util.List;

public class Task {
    private String id;
    private String name;
    private List<User> userList;

    public Task(String id, String name, List<User> userList){
        this.name = name;
        this.userList = userList;
    }

    public String getId() {
        return id;
    }

    public List<User> getUserList() {
        return userList;
    }

    public String getName() {
        return name;
    }
}
