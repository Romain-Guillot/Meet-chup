package com.progmobile.meetchup.models;

public class Comment {
    private String id;
    private User user;
    private String comment;

    public Comment(String id, User user, String comment) {
        this.id = id;
        this.user = user;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public User getUser() {
        return user;
    }
}
