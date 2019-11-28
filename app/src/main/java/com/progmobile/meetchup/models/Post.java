package com.progmobile.meetchup.models;

import java.util.Date;
import java.util.List;

public class Post extends Model {
    private User user;
    private Date date;
    private String description;
    private Document document;
    private List<Comment> commentsList;

    public Post(String id, User user, Date date, String description, Document document, Date dateCreated) {
        super(id, dateCreated);
        this.user = user;
        this.date = date;
        this.description = description;
        this.document = document;
        this.commentsList = null;
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
