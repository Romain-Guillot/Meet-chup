package com.example.appprojet.repositories;


import com.example.appprojet.models.Event;
import com.example.appprojet.models.Post;
import com.example.appprojet.models.User;
import com.example.appprojet.utils.Callback;

import java.util.List;

public interface IEventsDataRepository {

    public void getUserEvents(Callback<List<Event>> callback);


    public void createEvent(Event event, Callback<Event> callback);

    public void getEvent(String event_id, Callback<Event> callback);

    public void joinEvent(String eventID, Callback<Event> callback);

    public void modifyEvent(Event event, Callback<Event> callback);

    public void deleteEvent(Event event, Callback<Boolean> callback);


    public void loadEventPosts(Event event, Callback<Event> callback);

    public void loadEventToDoList(Event event, Callback<Event> callback);


    public void getPost(String post_id, Callback<Post> callback);

    public void addPost(Event event, Post post, Callback<Post> callback);

    public void deletePost(Event event, Post post, Callback<Boolean> callback);

    public void loadPostComments(Post post, Callback<Post> callback);

}
