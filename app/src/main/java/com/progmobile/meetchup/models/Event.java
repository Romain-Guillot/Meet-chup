package com.progmobile.meetchup.models;


import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.PropertyName;
import com.progmobile.meetchup.utils.Location;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Event extends Model{

    public final static String EVENT_COL = "events";
    public final static String EVENT_FIELD_INVITKEY = "invitationKey";
    public final static String EVENT_FIELD_PARTICIPANTS = "participantIDs";
    public final static String EVENT_FIELD_TITLE = "title";
    public final static String EVENT_FIELD_DESCRIPTION = "description";
    public final static String EVENT_FIELD_DATE_BEGIN = "dateBegin";
    public final static String EVENT_FIELD_DATE_END = "dateEnd";
    public final static String EVENT_FIELD_LOCATION = "location";


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

    @PropertyName(EVENT_FIELD_TITLE)
    public String getTitle() {
        return title;
    }

    @PropertyName(EVENT_FIELD_DESCRIPTION)
    public String getDescription() {
        return description;
    }

    @PropertyName(EVENT_FIELD_DATE_BEGIN)
    public Date getDateBegin() {
        return dateBegin;
    }

    @PropertyName(EVENT_FIELD_DATE_END)
    public Date getDateEnd() {
        return dateEnd;
    }


    @PropertyName(EVENT_FIELD_PARTICIPANTS)
    public List<String> getParticipantsID() {
        List<String> participantsID = new ArrayList<>();
        if (participants != null)
            for (User p : participants)
                participantsID.add(p.getId());
        return participantsID;
    }

    @PropertyName(EVENT_FIELD_LOCATION)
    public Location getLocation() {
        return location;
    }

    @PropertyName(EVENT_FIELD_INVITKEY)
    public String getInvitationKey() {
        return invitationKey;
    }

    @Exclude
    public List<User> getParticipants() {
        return participants;
    }

    @Exclude
    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void addParticipant(User user) {
        if (participants == null)
            participants = new ArrayList<>();
        this.participants.add(user);
    }

    @Override
    public int compareTo(Model o) {
        if (o.dateCreated == null && dateCreated == null) return ((Event) o).title.compareTo(title);
        if (o.dateCreated == null) return -1;
        if (dateCreated == null) return 1;
        return super.compareTo(o);
    }
}
