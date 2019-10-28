package com.example.appprojet.models;

import java.util.List;

public class Post {
    private User user;
    private String description;
    private Document document;
    private List<Comment> commentsList;

    public Post(User user, String description, Document document) {
        this.user = user;
        this.description = description;
        this.document = document;
        this.commentsList = null;
    }

    public List<Comment> getCommentsList() {
        return commentsList;
    }

    public User getUser() {
        return user;
    }

    public Document getDocument() {
        return document;
    }

    public String getDescription() {
        return description;
    }
}
