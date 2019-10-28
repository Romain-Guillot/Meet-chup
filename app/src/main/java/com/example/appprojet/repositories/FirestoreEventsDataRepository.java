package com.example.appprojet.repositories;


import com.example.appprojet.models.Document;
import com.example.appprojet.models.Event;
import com.example.appprojet.models.Location;
import com.example.appprojet.models.Post;
import com.example.appprojet.models.User;
import com.example.appprojet.utils.Callback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
        User u1 = new User("Romain", "", "");
        User u2 = new User("Alexis", "", "");
        User u3 = new User("Corentin", "", "");
        User u4 = new User("Jean", "", "");

        List<Event> fakeEvents = new ArrayList<>();
        fakeEvents.add(
            new Event(
                "Week-end au ski",
                "Un super week-end de malade",
                Arrays.asList(u1, u2),
                new Date(2019, 4, 21),
                new Date(2019, 6, 12),
                    new Date(2019, 1, 2),
                new Location(25.4d, 29.7d))
        );
        fakeEvents.add(
            new Event(
                "Anniversaire de Tonton Michel",
                "Merguez et saucisse seront au rendez-vous",
                Arrays.asList(u1, u3, u4),
                new Date(2019, 12, 25),
                null,
                new Date(2019, 1, 1),
                new Location(25.4d, 29.7d))
        );

        callback.onSucceed(fakeEvents);
    }


    @Override
    public void createEvent(User user, Event event, Callback<Event> callback) {
        callback.onSucceed(event);
    }

    @Override
    public void getEvent(User user, String event_id, Callback<Event> callback) {
        User u1 = new User("Romain", "", "");
        User u2 = new User("Alexis", "", "");
        User u3 = new User("Corentin", "", "");

        callback.onSucceed(new Event(
            "Anniversaire de Tonton Michel",
            "Merguez et saucisse seront au rendez-vous",
            Arrays.asList(u1, u3, u2),
            new Date(2019, 12, 25),
            new Date(2020, 1, 10),
            new Date(2019, 1, 1),
            new Location(40.7128, -74.0060)));
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
        ArrayList<Post> posts = new ArrayList<>();
        posts.add(new Post("Un premier post", new Document("https://i.imgur.com/WHRgwnI.jpg")));
        posts.add(new Post("Michel", new Document("https://www.plethorist.com/wp-content/uploads/2017/07/The-Worst-Stock-Photos-On-The-Internet-2.jpg")));
        posts.add(new Post("Huguette à la plage", new Document("https://www.demilked.com/magazine/wp-content/uploads/2018/03/5aaa1cc45a750-funny-weird-wtf-stock-photos-4-5a3927b70f562__700.jpg")));
        event.setPosts(posts);

        callback.onSucceed(event);
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
