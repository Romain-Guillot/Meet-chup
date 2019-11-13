package com.progmobile.meetchup.models;

import java.util.List;

public class User {

    private String id;
    private String name;
    private String email;
    private List<Event> eventList;
    private boolean isFirstLogIn;

    public User(String id, String name, String email) {
        this(id, name, email, false);
    }

    public User(String id, String name, String email, boolean isFirstLogIn) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.eventList = null;
        this.isFirstLogIn = isFirstLogIn;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public boolean isFirstLogIn() {
        return isFirstLogIn;
    }

}
