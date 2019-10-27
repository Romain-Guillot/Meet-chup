package com.example.appprojet.repositories;


import com.example.appprojet.models.Event;
import com.example.appprojet.models.Post;
import com.example.appprojet.models.User;
import com.example.appprojet.utils.Callback;

import java.util.List;

public interface IEventsDataRepository {

    public void getUserEvents(User user, Callback<List<Event>> callback);


    public void createEvent(User user, Event event, Callback<Event> callback);

    public void getEvent(User user, String event_id, Callback<Event> callback);

    public void joinEvent(User user, String eventID, Callback<Event> callback);

    public void modifyEvent(User user, Event event, Callback<Event> callback);

    public void deleteEvent(User user, Event event, Callback<Boolean> callback);


    public void loadEventPosts(User user, Event event, Callback<Event> callback);

    public void loadEventToDoList(User user, Event event, Callback<Event> callback);


    public void addPost(User user, Event event, Post post, Callback<Post> callback);

    public void deletePost(User user, Event event, Post post, Callback<Boolean> callback);



}
