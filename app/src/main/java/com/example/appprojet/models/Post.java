package com.example.appprojet.models;

import java.util.List;

public class Post {
    private String description;
    private Document document;
    private List<Comment> commentsList;

    public Post(String description, Document document){
        this.description = description;
        this.document = document;
        this.commentsList = null;
    }

    public List<Comment> getCommentsList() {
        return commentsList;
    }

    public Document getDocument() {
        return document;
    }

    public String getDescription() {
        return description;
    }
}
