package com.progmobile.meetchup.models;

import java.util.Date;
import java.util.List;

public class Task extends Model {
    private String id;
    private String name;
    private List<User> userList;

    public Task(String id, String name, List<User> userList, Date dateCreated) {
        super(id, dateCreated);
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
