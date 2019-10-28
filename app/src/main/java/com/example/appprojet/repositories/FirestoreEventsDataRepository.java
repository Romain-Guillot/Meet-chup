package com.example.appprojet.repositories;


import com.example.appprojet.models.Event;
import com.example.appprojet.models.Location;
import com.example.appprojet.models.Post;
import com.example.appprojet.models.User;
import com.example.appprojet.utils.Callback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FirestoreEventsDataRepository implements IEventsDataRepository {

    private static FirestoreEventsDataRepository instance = null;

    private IAuthenticationRepository authRepo = null;

    private Map<String, Event> fakeEvents = new HashMap<>();
    private Map<String, User> fakeUsers = new HashMap<>();
    private Map<String, List<Post>> fakePosts = new HashMap<>();

    private FirestoreEventsDataRepository() {
        authRepo = FirebaseAuthenticationRepository.getInstance();
        fakeUsers.put("1", new User("Romain", "", ""));
        fakeUsers.put("2", new User("Alexis", "", ""));
        fakeUsers.put("3", new User("Corentin", "", ""));
        fakeUsers.put("4", new User("Jean", "", ""));


        fakeEvents.put("1", new Event(
                "1",
                "Week-end au ski",
                "Un super week-end de malade",
                Arrays.asList(fakeUsers.get("1"), fakeUsers.get("2")),
                new Date(2019, 4, 21),
                new Date(2019, 6, 12),
                new Date(2019, 1, 2),
                new Location(25.4d, 29.7d))
        );

        fakeEvents.put("2", new Event(
                "2",
                "Anniversaire de Tonton Michel",
                "Merguez et saucisse seront au rendez-vous",
                Arrays.asList(fakeUsers.get("1"), fakeUsers.get("2"), fakeUsers.get("4")),
                new Date(2019, 12, 25),
                null,
                new Date(2019, 1, 1),
                new Location(25.4d, 29.7d))
        );

        List<Post> postsEvent1 = Arrays.asList(
                new Post("", null, null, "Un premier post", new Document("", "https://i.imgur.com/WHRgwnI.jpg")),
                new Post("", null, null, "Bla bla bla", new Document("", "https://www.plethorist.com/wp-content/uploads/2017/07/The-Worst-Stock-Photos-On-The-Internet-2.jpg")),
                new Post("", null, null, "Michel à la plage", new Document("", "https://www.demilked.com/magazine/wp-content/uploads/2018/03/5aaa1cc45a750-funny-weird-wtf-stock-photos-4-5a3927b70f562__700.jpg"))
        );

        fakePosts.put("1", postsEvent1);
        fakePosts.put("2", null);
    }


    public static FirestoreEventsDataRepository getInstance() {
        synchronized (FirestoreEventsDataRepository.class) {
            if (instance == null)
                instance = new FirestoreEventsDataRepository();
            return instance;
        }
    }


    @Override
    public void getUserEvents(Callback<List<Event>> callback) {
        callback.onSucceed(new ArrayList<>(fakeEvents.values()));
    }


    @Override
    public void createEvent(Event event, Callback<Event> callback) {
        callback.onSucceed(event);
    }

    @Override
    public void joinEvent(String eventID, Callback<Event> callback) {

    }

    @Override
    public void modifyEvent(Event event, Callback<Event> callback) {

    }

    @Override
    public void deleteEvent(Event event, Callback<Boolean> callback) {

    }

    @Override
    public void loadEventPosts(Event event, Callback<Event> callback) {
        List<Post> postsEvents = fakePosts.get(event.getId());
        event.setPosts(postsEvents);
        callback.onSucceed(event);
    }

    @Override
    public void loadEventToDoList(Event event, Callback<Event> callback) {

    }

    @Override
    public void addPost(Event event, Post post, Callback<Post> callback) {

    }

    @Override
    public void deletePost(Event event, Post post, Callback<Boolean> callback) {

    }
}
