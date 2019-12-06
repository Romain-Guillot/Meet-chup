package com.progmobile.meetchup.repositories;


import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.progmobile.meetchup.models.Event;
import com.progmobile.meetchup.models.Post;
import com.progmobile.meetchup.models.User;
import com.progmobile.meetchup.utils.CallbackException;
import com.progmobile.meetchup.utils.Callback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * Use only method with "status : done"
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public class FirestoreEventsDataRepository implements IEventsDataRepository {

    private static FirestoreEventsDataRepository instance = null;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;





    private FirestoreEventsDataRepository() {
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }


    public static FirestoreEventsDataRepository getInstance() {
        synchronized (FirestoreEventsDataRepository.class) {
            if (instance == null)
                instance = new FirestoreEventsDataRepository();
            return instance;
        }
    }


    /** @inheritDoc - Status : IN PROGRESS !!!!!!
    * */
    @Override
    public void allEvents(@NonNull Activity client, @NonNull Callback<List<Event>> callback) {
        FirebaseUser fbUser = firebaseAuth.getCurrentUser();
        if (fbUser == null) {
            callback.onFail(new CallbackException(CallbackException.Type.NO_LOGGED)); return ;
        }
        firestore.collection(User.USERS_COL).document(fbUser.getUid()).addSnapshotListener(client, (documentSnapshot, e1) -> {
            if (e1 != null) { callback.onFail(CallbackException.fromFirebaseException(e1));return ; }
            try {
                List<String> eventIDs = (List<String>) documentSnapshot.getData().get(User.USERS_FIELD_EVENTS);
                List<Event> events = new ArrayList<>();
                if (eventIDs.isEmpty()) {
                    callback.onSucceed(events);
                    return ;
                }
                for (String id : eventIDs) {
                    firestore.collection(Event.EVENT_COL).document(id).get().addOnCompleteListener(task -> {
                        try {
                            if (task.isSuccessful()) {
                                Event event = task.getResult().toObject(Event.class);
                                if (event != null) {
                                    event.setId(task.getResult().getId());
                                    events.add(event);
                                    Collections.sort(events);
                                }
                            }
                        } catch (Exception e3) {
                        } finally {callback.onSucceed(events);}
                    });
                }
            } catch (Exception e2) { callback.onSucceed(new ArrayList<>()); }
        });
    }

    /** @inheritDoc - Status : DONE
     * Load the event with the corresponding ID and if the deserialization succeeds the event is returned */
    @Override
    public void getEvent(@NonNull Activity client, @NonNull String eventID, @NonNull Callback<Event> callback) {
        firestore.collection(Event.EVENT_COL).document(eventID).addSnapshotListener(client, ((documentSnapshot, e) -> {
            if (e != null || documentSnapshot == null && !documentSnapshot.exists()) {
                callback.onFail(CallbackException.fromFirebaseException(e)); return ;
            }
            Event event = documentSnapshot.toObject(Event.class);
            event.setId(documentSnapshot.getId());
            if (event != null) callback.onSucceed(event);
            else callback.onFail(new CallbackException());
        }));
    }

    /** @inheritDoc - Status : IN PROGRESS
     * */
    @Override
    public void createEvent(@NonNull Event event, @NonNull Callback<String> callback) {
        FirebaseUser fbUser = firebaseAuth.getCurrentUser();
        if (fbUser == null) {
            callback.onFail(new CallbackException(CallbackException.Type.NO_LOGGED));
            return ;
        }
        event.addParticipant(new User(firebaseAuth.getUid(), null, null));

        WriteBatch batch = firestore.batch();
        DocumentReference docUser = firestore.collection(User.USERS_COL).document(fbUser.getUid());
        DocumentReference docEvent = firestore.collection(Event.EVENT_COL).document(); // auto generated ID
        batch.set(docUser, new HashMap<String, Object>(){{put(User.USERS_FIELD_EVENTS, FieldValue.arrayUnion(docEvent.getId()));}}, SetOptions.merge());
        batch.set(docEvent, event);
        batch.commit().addOnCompleteListener(t -> {
           if (t.isSuccessful()) {
                callback.onSucceed(docEvent.getId());
           } else {
               callback.onFail(CallbackException.fromFirebaseException(t.getException()));
           }
        });
    }

    /** @inheritDoc - Status : DONE
     *  Look if a document with this key exists, if not the new key is set to the event */
    @Override
    public void updateEventInvitationKey(@NonNull String eventID, @NonNull String key, @NonNull Callback<String> callback) {
        firestore.collection(Event.EVENT_COL).whereEqualTo(Event.EVENT_FIELD_INVITKEY, key).get().addOnCompleteListener(task1 -> {
            if (task1.isSuccessful() && task1.getResult() != null && task1.getResult().getDocuments().size() >= 1 && !task1.getResult().getDocuments().get(0).getId().equals(eventID))  {
                callback.onFail(new CallbackException(CallbackException.Type.INVITATION_KEY_COLLISION));
            } else {
                firestore.collection(Event.EVENT_COL).document(eventID)
                        .set(new HashMap<String, String>(){{put(Event.EVENT_FIELD_INVITKEY, key);}}, SetOptions.merge())
                        .addOnCompleteListener(task2 -> {
                            if (task2.isSuccessful()) callback.onSucceed(key);
                            else callback.onFail(CallbackException.fromFirebaseException(task2.getException()));
                        });
            }
        });
    }

    /** @inheritDoc - Status : DONE
     * Just delete the invitation field of the event */
    @Override
    public void deleteEventInvitationKey(@NonNull String eventID, @NonNull Callback<Void> callback) {
        firestore.collection(Event.EVENT_COL).document(eventID)
                .set(new HashMap<String, Object>(){{put(Event.EVENT_FIELD_INVITKEY, FieldValue.delete());}}, SetOptions.merge())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) callback.onSucceed(null);
                    else callback.onFail(CallbackException.fromFirebaseException(task.getException()));
                });
    }

    /** @inheritDoc - Status : DONE
     * Get the correct event doc, add the user to it, add the event ID in the user event list */
    @Override
    public void joinEvent(@NonNull String invitationKey, @NonNull Callback<String> callback) {
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fbUser == null) {
            callback.onFail(new CallbackException(CallbackException.Type.NO_LOGGED));return ;
        }
        firestore.collection(Event.EVENT_COL).whereEqualTo(Event.EVENT_FIELD_INVITKEY, invitationKey).get().addOnCompleteListener(task1 -> {
            if (task1.isSuccessful() && task1.getResult() != null && task1.getResult().getDocuments().size() == 1) {
                DocumentSnapshot docEventSnap = task1.getResult().getDocuments().get(0);
                String eventID = docEventSnap.getId();
                String userID = fbUser.getUid();
                WriteBatch writeBatch = firestore.batch();
                DocumentReference eventDocRef = docEventSnap.getReference();
                DocumentReference userDocRef = firestore.collection(User.USERS_COL).document(userID);
                writeBatch.set(userDocRef, new HashMap<String, Object>(){{put(User.USERS_FIELD_EVENTS, FieldValue.arrayUnion(eventID));}}, SetOptions.merge());
                writeBatch.set(eventDocRef, new HashMap<String, Object>(){{put(Event.EVENT_FIELD_PARTICIPANTS, FieldValue.arrayUnion(userID));}}, SetOptions.merge());
                writeBatch.commit().addOnCompleteListener(task2 -> {
                    if (task2.isSuccessful())  callback.onSucceed(eventID);
                    else callback.onFail(CallbackException.fromFirebaseException(task2.getException()));
                });
            } else {
                callback.onFail(CallbackException.fromFirebaseException(task1.getException()));
            }
        });
    }

    /** @inheritDoc - Status : DONE
     *  Update the user event list and the event user list */
    @Override
    public void quitEvent(@NonNull String eventID, @NonNull Callback<Void> callback) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null) {
            callback.onFail(new CallbackException(CallbackException.Type.NO_LOGGED));return ;
        }
        WriteBatch writeBatch = firestore.batch();
        DocumentReference userDoc = firestore.collection(User.USERS_COL).document(user.getUid());
        DocumentReference eventDoc = firestore.collection(Event.EVENT_COL).document(eventID);
        writeBatch.set(userDoc, new HashMap<String, Object>(){{put(User.USERS_FIELD_EVENTS, FieldValue.arrayRemove(eventID));}}, SetOptions.merge());
        writeBatch.set(eventDoc, new HashMap<String, Object>(){{put(Event.EVENT_FIELD_PARTICIPANTS, FieldValue.arrayRemove(user.getUid()));}}, SetOptions.merge());
        writeBatch.commit().addOnCompleteListener(task -> {
            if (task.isSuccessful()) callback.onSucceed(null);
            else callback.onFail(CallbackException.fromFirebaseException(task.getException()));
        });
    }

    /**
     * @inheritDoc - Status : DONE
     * Just update (with merge) the event doc
     */
    @Override
    public void updateEvent(@NonNull String eventID, @NonNull Event event, Callback<String> callback) {
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fbUser == null) {
            callback.onFail(new CallbackException(CallbackException.Type.NO_LOGGED));return ;
        }
        Map<String, Object> updateMap = new HashMap<String, Object>(){{
            put(Event.EVENT_FIELD_TITLE, event.getTitle());
            put(Event.EVENT_FIELD_DATE_BEGIN, event.getDateBegin());
            put(Event.EVENT_FIELD_DATE_END, event.getDateEnd());
            put(Event.EVENT_FIELD_LOCATION, event.getLocation());
            put(Event.EVENT_FIELD_DESCRIPTION, event.getDescription());
        }};
        firestore.collection(Event.EVENT_COL).document(eventID).set(updateMap, SetOptions.merge()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) callback.onSucceed(eventID);
            else callback.onFail(CallbackException.fromFirebaseException(task.getException()));
        });
    }

    /**
     * Get list of posts
     * The list is returned through the callback if success
     */
    @Override
    public ListenerRegistration allPosts(@NonNull String eventID, Callback<List<Post>> callback) {
        FirebaseUser fbUsr = FirebaseAuth.getInstance().getCurrentUser();
        if (fbUsr == null) {
            callback.onFail(new CallbackException(CallbackException.Type.NO_LOGGED)); return null;
        }
        return firestore.collection(Event.EVENT_COL).document(eventID).collection(Post.POST_COL).addSnapshotListener((docsSnap, e) -> {
            if (e != null || docsSnap == null) {
                callback.onFail(CallbackException.fromFirebaseException(e)); return ; }
            List<Post> posts = new ArrayList<>();
            for (QueryDocumentSnapshot docSnap : docsSnap) {
                try {
                    Post p = docSnap.toObject(Post.class);
                    p.setId(docSnap.getId());
                    String userID = p.getUserID();
                    System.err.println(userID);
                    if (userID != null) {
                        firestore.collection(User.USERS_COL).document(p.getUserID()).get().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Map<String, Object> data = task.getResult().getData();
                                p.setUser(new User(userID, (String)data.get(User.USER_NAME_FIELD), null));
                            }
                            posts.add(p);
                            Collections.sort(posts);
                            callback.onSucceed(posts);
                        });
                    } else {
                        posts.add(p);
                    }
                } catch (Exception e2) {e2.printStackTrace();}
            }
            Collections.sort(posts);
            callback.onSucceed(posts);
        });
    }


}
