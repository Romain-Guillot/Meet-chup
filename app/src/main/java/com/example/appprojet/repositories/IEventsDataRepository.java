package com.example.appprojet.repositories;


public interface IEventsDataRepository {


    public void createEvent(/*User user, Event event, Callback<Event> callback*/);

    public void joinEvent(/*User user, String eventID*, Callback<Event> callback*/);

    public void mofifyEvent(/*User user, Event event, Callback<Event> callback*/);

    public void deleteEvent(/*User user, Event event, Callback<Boolean> callback*/);


    public void getUserEvents(/*User user, Callback<List<Event>> callback*/);


    public void loadEventPosts(/*User user, Event event, Callback<Event> callback */);

    public void loadEventToDoList(/*User user, Event event, Callback<Event> callback */);


    public void addPost(/*User user, Event event, Post post, Callback<Post> callback*/);

    public void deletePost(/*User user, Event event, Post post, Callback<Boolean> callback*/);

}
