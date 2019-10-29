package com.example.appprojet.models;

import java.util.Date;
import java.util.List;

public class Post {
    private String id;
    private User user;
    private Date date;
    private String description;
    private Document document;
    private List<Comment> commentsList;

    public Post(String id, User user, Date date, String description, Document document) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.description = description;
        this.document = document;
        this.commentsList = null;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public Document getDocument() {
        return document;
    }

    public List<Comment> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comment> commentsList) {
        this.commentsList = commentsList;
    }
}
