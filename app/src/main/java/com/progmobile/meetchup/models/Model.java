package com.progmobile.meetchup.models;

import com.google.firebase.firestore.Exclude;

import java.util.Date;

public class Model {
    protected String id;
    protected Date dateCreated;

    public Model(String id, Date dateCreated) {
        this.id = id;
        this.dateCreated = dateCreated;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }
}
