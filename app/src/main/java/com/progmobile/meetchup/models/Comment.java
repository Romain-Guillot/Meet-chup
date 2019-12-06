package com.progmobile.meetchup.models;

import java.util.Date;

public class Comment extends Model{
    private User user;
    private String comment;

    public Comment(String id, User user, String comment, Date createdDate) {
        super(id, createdDate);
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
