package com.example.appprojet.repositories;


import com.example.appprojet.models.Comment;
import com.example.appprojet.models.Document;
import com.example.appprojet.models.Event;
import com.example.appprojet.utils.CallbackException;
import com.example.appprojet.utils.Location;
import com.example.appprojet.models.Post;
import com.example.appprojet.models.User;
import com.example.appprojet.utils.Callback;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FirestoreEventsDataRepository implements IEventsDataRepository {

    private static FirestoreEventsDataRepository instance = null;

    private IAuthenticationRepository authRepo;
    private FirebaseFirestore firestore;

    private final static String EVENT_COL = "events";
    private final static String EVENT_FIELD_INVITKEY = "invitKey";


    private Map<String, Event> fakeEvents = new HashMap<>();
    private Map<String, User> fakeUsers = new HashMap<>();
    private Map<String, Post> fakePosts = new HashMap<>();
    private Map<String, List<Post>> postByEventId = new HashMap<>();

    private FirestoreEventsDataRepository() {
        firestore = FirebaseFirestore.getInstance();
        authRepo = FirebaseAuthenticationRepository.getInstance();
        fakeUsers.put("1", new User("Romain", "", ""));
        fakeUsers.put("2", new User("Alexis", "", ""));
        fakeUsers.put("3", new User("Corentin", "", ""));
        fakeUsers.put("4", new User("Jean", "", ""));

        Calendar begin = new GregorianCalendar();Calendar end = new GregorianCalendar();;Calendar created = new GregorianCalendar();
        begin.set(2019, 4, 21); end.set(2019, 6, 12);created.set(2019, 1, 1);
        fakeEvents.put("1", new Event(
                "1",
                "Week-end au ski",
                "Un super week-end de malade",
                Arrays.asList(fakeUsers.get("1"), fakeUsers.get("2")),
                begin.getTime(),
                end.getTime(),
                created.getTime(),
                new Location(25.4d, 29.7d),
                "dddd")
        );

        begin.set(2019, 12, 24); end.set(2020, 1, 12);created.set(2019, 1, 2);
        fakeEvents.put("2", new Event(
                "2",
                "Anniversaire de Tonton Michel",
                "Merguez et saucisse seront au rendez-vous",
                Arrays.asList(fakeUsers.get("1"), fakeUsers.get("2"), fakeUsers.get("4")),
                begin.getTime(),
                null,
                created.getTime(),
                new Location(40.7128, -74.0060),
                null)
        );

        fakePosts.put("1", new Post("1", fakeUsers.get("4"), null, "Un premier post", new Document("", "https://i.imgur.com/WHRgwnI.jpg")));
        fakePosts.put("2", new Post("2", fakeUsers.get("2"), null, "Bla bla bla", new Document("", "https://www.plethorist.com/wp-content/uploads/2017/07/The-Worst-Stock-Photos-On-The-Internet-2.jpg")));
        fakePosts.put("3",new Post("3", fakeUsers.get("3"), null, "Michel à la plage", new Document("", "https://www.demilked.com/magazine/wp-content/uploads/2018/03/5aaa1cc45a750-funny-weird-wtf-stock-photos-4-5a3927b70f562__700.jpg")));

        postByEventId.put("1", Arrays.asList(fakePosts.get("1"), fakePosts.get("2"), fakePosts.get("3")));


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
    public void getEvent(String event_id, Callback<Event> callback) {
        callback.onSucceed(fakeEvents.get(event_id));
    }


    public void joinEvent(String eventID, Callback<Event> callback) {

    }

    // DONE
    @Override
    public void updateEventInvitationKey(String eventID, String key, Callback<String> callback) {
        firestore.collection(EVENT_COL).document(eventID)
                .set(new HashMap<String, String>(){{put(EVENT_FIELD_INVITKEY, key);}}, SetOptions.merge())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) callback.onSucceed(key);
                    else callback.onFail(CallbackException.fromFirebaseException(task.getException()));
                });
    }

    // DONE
    @Override
    public void removeEventInvitationKey(String eventID, Callback<Void> callback) {
        firestore.collection(EVENT_COL).document(eventID)
                .update(new HashMap<String, Object>(){{put(EVENT_FIELD_INVITKEY, FieldValue.delete());}})
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) callback.onSucceed(null);
                    else callback.onFail(CallbackException.fromFirebaseException(task.getException()));
                });
    }

    @Override
    public void deleteEvent(String eventID, Callback<Void> callback) {

    }

    @Override
    public void loadEventPosts(String eventID, Callback<Event> callback) {
//        List<Post> postsEvents = postByEventId.get(e());
//        event.setPosts(postsEvents);
//        callback.onSucceed(event);
    }

    @Override
    public void loadEventToDoList(String eventID, Callback<Event> callback) {

    }

    @Override
    public void getPost(String post_id, Callback<Post> callback) {
        Post post = fakePosts.get(post_id);
        callback.onSucceed(post);
    }

    @Override
    public void addPost(String eventID, Post post, Callback<Post> callback) {

    }

    @Override
    public void deletePost(String eventID, Post post, Callback<Boolean> callback) {

    }

    @Override
    public void loadPostComments(Post post, Callback<Post> callback) {
        List<Comment> comments = Arrays.asList(
                new Comment("", fakeUsers.get("1"),"Pour savoir d'où vient le vent, faut mettre le doigt dans le cul du coq !" ),
                new Comment("", fakeUsers.get("2"),"Elle est où la poulette ?" ),
                new Comment("", fakeUsers.get("3"),"Tatan, elle fait du flan" ),
                new Comment("", fakeUsers.get("4"),"Dans trois jour tatan elle m'emmène à la mer pour me noyer"),
                new Comment("", fakeUsers.get("1"),"Pour savoir d'où vient le vent, faut mettre le doigt dans le cul du coq !" ),
                new Comment("", fakeUsers.get("2"),"Elle est où la poulette ?" ),
                new Comment("", fakeUsers.get("3"),"Tatan, elle fait du flan" ),
                new Comment("", fakeUsers.get("4"),"Dans trois jour tatan elle m'emmène à la mer pour me noyer"),
                new Comment("", fakeUsers.get("1"),"Pour savoir d'où vient le vent, faut mettre le doigt dans le cul du coq !" ),
                new Comment("", fakeUsers.get("2"),"Elle est où la poulette ?" ),
                new Comment("", fakeUsers.get("3"),"Tatan, elle fait du flan" ),
                new Comment("", fakeUsers.get("4"),"Dans trois jour tatan elle m'emmène à la mer pour me noyer")
        );
        post.setCommentsList(comments);
        callback.onSucceed(post);
    }
}
