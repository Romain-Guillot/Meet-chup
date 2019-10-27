package com.example.appprojet.models;

public class Comment {
    private User user;
    private String comment;

    public Comment(User user, String comment){
        this.user = user;
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public User getUser() {
        return user;
    }
}
