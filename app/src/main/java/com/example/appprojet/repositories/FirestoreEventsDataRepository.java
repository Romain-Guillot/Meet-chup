package com.example.appprojet.repositories;


import com.example.appprojet.models.Event;
import com.example.appprojet.models.Post;
import com.example.appprojet.models.User;
import com.example.appprojet.utils.Callback;

import java.util.List;


public class FirestoreEventsDataRepository implements IEventsDataRepository {

    private static FirestoreEventsDataRepository instance = null;

    private FirestoreEventsDataRepository() {

    }

    public static FirestoreEventsDataRepository getInstance() {
        synchronized (FirestoreEventsDataRepository.class) {
            if (instance == null)
                instance = new FirestoreEventsDataRepository();
            return instance;
        }
    }


    @Override
    public void getUserEvents(User user, Callback<List<Event>> callback) {

    }

    @Override
    public void createEvent(User user, Event event, Callback<Event> callback) {

    }

    @Override
    public void joinEvent(User user, String eventID, Callback<Event> callback) {

    }

    @Override
    public void modifyEvent(User user, Event event, Callback<Event> callback) {

    }

    @Override
    public void deleteEvent(User user, Event event, Callback<Boolean> callback) {

    }

    @Override
    public void loadEventPosts(User user, Event event, Callback<Event> callback) {

    }

    @Override
    public void loadEventToDoList(User user, Event event, Callback<Event> callback) {

    }

    @Override
    public void addPost(User user, Event event, Post post, Callback<Post> callback) {

    }

    @Override
    public void deletePost(User user, Event event, Post post, Callback<Boolean> callback) {

    }
}
