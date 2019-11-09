package com.example.appprojet.repositories;


import com.example.appprojet.models.Event;
import com.example.appprojet.models.Post;
import com.example.appprojet.utils.Callback;

import java.util.List;

public interface IEventsDataRepository {

    public void getUserEvents(Callback<List<Event>> callback);


    public void createEvent(Event event, Callback<Event> callback);

    public void getEvent(String eventID, Callback<Event> callback);

    public void joinEvent(String eventID, Callback<Event> callback);

    public void deleteEvent(String eventID, Callback<Void> callback);

    public void updateEventInvitationKey(String eventID, String key, Callback<String> callback);

    public void removeEventInvitationKey(String eventID, Callback<Void> callback);


    public void loadEventPosts(String eventID, Callback<Event> callback);

    public void loadEventToDoList(String eventID, Callback<Event> callback);


    public void getPost(String post_id, Callback<Post> callback);

    public void addPost(String eventID, Post post, Callback<Post> callback);

    public void deletePost(String eventID,Post post, Callback<Boolean> callback);

    public void loadPostComments(Post post, Callback<Post> callback);

}
