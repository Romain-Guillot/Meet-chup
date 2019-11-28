package com.progmobile.meetchup.models;

import com.google.firebase.firestore.Exclude;

import java.util.Date;

/**
 * Super model destined to be store in database
 */
public abstract class Model implements Comparable<Model> {
    /**
     * Unique identifier of the object inside the database
     */
    protected String id;

    /**
     * Date of creation, to provide an order on models to sort them
     */
    protected Date dateCreated;

    public Model() {

    }

    public Model(String id, Date dateCreated) {
        this.id = id;
        this.dateCreated = dateCreated;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public int compareTo(Model o) {
        return o.dateCreated.compareTo(this.dateCreated);
    }
}
