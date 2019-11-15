package com.progmobile.meetchup.models;


import com.google.firebase.firestore.Exclude;
import com.progmobile.meetchup.utils.Location;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Event extends Model{

    private String title;
    private String description;

    private Date dateBegin;
    private Date dateEnd;
    private Location location;
    private String invitationKey;
    private List<User> participants;
    private List<Post> posts;

    public Event() {
        super();
    }

    public Event(String id, String title, String description, List<User> participants, Date dateBegin, Date dateEnd, Date dateCreated, Location location, String invitationKey) {
        super(id, dateCreated);
        this.title = title;
        this.description = description;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.participants = participants;
        this.location = location;
        this.invitationKey = invitationKey;
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

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<String> getParticipantsID() {
        List<String> participantsID = new ArrayList<>();
        for (User p : participants)
            participantsID.add(p.getId());
        return participantsID;
    }

    public void addParticipant(User user) {
        if (participants == null)
            participants = new ArrayList<>();
        this.participants.add(user);
    }

    @Exclude
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
