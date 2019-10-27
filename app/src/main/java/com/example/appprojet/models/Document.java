package com.example.appprojet.models;

import java.util.List;

public class Document {
    private String url;
    private List<Comment> commentsList;

    public Document(String url){
        this.url = url;
        this.commentsList = null;
    }

    public List<Comment> getCommentsList() {
        return commentsList;
    }

    public String getUrl() {
        return url;
    }
}
