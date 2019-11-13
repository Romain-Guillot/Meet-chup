package com.progmobile.meetchup.models;


import com.progmobile.meetchup.utils.Location;

import java.util.Date;
import java.util.List;


public class Event {
    private String id;
    private String title;
    private String description;
    private Date dateCreated;
    private Date dateBegin;
    private Date dateEnd;
    private Location location;
    private String invitationKey;
    private List<User> participants;
    private List<Post> posts;


    public Event(String id, String title, String description, List<User> participants, Date dateBegin, Date dateEnd, Date dateCreated, Location location, String invitationKey) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.dateCreated = dateCreated;
        this.participants = participants;
        this.location = location;
        this.invitationKey = invitationKey;
        this.posts = null;
    }

    public String getId() {
        return id;
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

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public Location getLocation() {
        return location;
    }

    public String getInvitationKey() {
        return invitationKey;
    }
}
