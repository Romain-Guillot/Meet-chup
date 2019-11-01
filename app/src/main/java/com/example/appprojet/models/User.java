package com.example.appprojet.models;

import java.util.List;

public class User {

    private String id;
    private String name;
    private String email;
    private List<Event> eventList;

    private boolean isFirstLogIn = false;

    public User(String id, String name, String email){
        this.name = name;
        this.id = id;
        this.email = email;
        this.eventList = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isFirstLogIn() {
        return isFirstLogIn;
    }

    public void setFirstLogIn(boolean firstLogIn) {
        isFirstLogIn = firstLogIn;
    }
}
