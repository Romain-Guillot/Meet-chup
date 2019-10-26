package com.example.appprojet.repositories;


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
    public void createEvent() {

    }

    @Override
    public void joinEvent() {

    }

    @Override
    public void mofifyEvent() {

    }

    @Override
    public void deleteEvent() {

    }

    @Override
    public void getUserEvents() {

    }

    @Override
    public void loadEventPosts() {

    }

    @Override
    public void loadEventToDoList() {

    }

    @Override
    public void addPost() {

    }

    @Override
    public void deletePost() {

    }
}
