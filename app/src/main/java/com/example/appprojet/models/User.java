package com.example.appprojet.models;

import java.util.List;

public class User {
    private String name;
    private String id;
    private String email;
    private List<Event> eventList;

    public User(String name, String id, String email){
        this.name = name;
        this.id = id;
        this.email = email;
        this.eventList = null;
    }

    public String getName() {
        return name;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }
}
