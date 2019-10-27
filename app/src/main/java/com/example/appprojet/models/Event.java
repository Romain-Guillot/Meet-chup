package com.example.appprojet.models;


import java.util.Date;

import java.util.List;


public class Event {
    private String title;
    private String description;
    private List<User> participants;
    private List<Post> posts;
    private Date dateCreated;
    private Date dateBegin;
    private Date dateEnd;
    private Location localisation;

    public Event(String title, String description, List<User> participants, Date dateBegin, Date dateEnd, Date dateCreated, Location localisation){
        this.title = title;
        this.description = description;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.dateCreated = dateCreated;
        this.participants = participants;
        this.localisation = localisation;
        this.posts = null;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public Location getLocalisation() {
        return localisation;
    }
}
