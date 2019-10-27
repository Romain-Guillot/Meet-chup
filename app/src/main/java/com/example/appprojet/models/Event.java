package com.example.appprojet.models;

import android.location.Geocoder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Event {
    private String title;
    private String description;
    private List<User> participants;
    private List<Post> posts;
    private Date dateBegin;
    private Date dateEnd;
    private Geocoder localisation;

    public Event(String title, String description, List<User> participants, Date dateBegin, Date dateEnd, Geocoder localisation){
        this.title = title;
        this.description = description;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.participants = participants;
        this.localisation = localisation;
        this.posts = null;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public Geocoder getLocalisation() {
        return localisation;
    }
}
